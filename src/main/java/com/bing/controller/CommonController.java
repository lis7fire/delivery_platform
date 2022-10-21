package com.bing.controller;

import com.bing.common.ExceptionCodeEnum;
import com.bing.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

/**
 * 文件预览，上传，下载接口，响应头不一样，注意
 *
 * @author makejava
 * @since 2022-09-26 22:12:42
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${myProject.filePath}")
    private String filePath;

    @PostMapping(value = "/upload")
    public R<String> uploadFiles(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
//        前端传来的 file 是临时文件，需要转存落盘，否则本次请求完成后便会被删除
//        log.info("文件上传开始。。。获取到的文件：{}", file.toString());
//        原文件名： aaa.jpg ，suffix 为后缀格式.jpg
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//UUID随机生成新文件名 1231313 .jpg
        String fileName = UUID.randomUUID().toString() + suffix;
//        File dir = new File(filePath);
//        if (!dir.exists()) {
//            // 文件夹不存在就新建
//            dir.mkdir();
//        }
        try {
            // 将临时文件 file 转存到根文件夹
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            log.error(e.getMessage());
//            e.printStackTrace();
            return R.fail(ExceptionCodeEnum.IO_EXCEPTION);
        }
        return R.success(fileName); // 将新文件名返回前端报存到DB
    }

    /**
     * 方法1：使用response输出流下载文件给客户端，此方法必须返回void；通过IO读取server的输入流，在输出到响应中。
     *
     * @param name 前端需要的文件名
     * @return
     * @author: LiBingYan
     * @时间: 2022/9/30
     */
    @GetMapping(value = "/download")
    public void previewImage(String name, HttpServletResponse response) throws IOException {
//        log.info("文件下载开始。。。");

        // 通过 server IO 读取文件，获取输入流
//            byte[] fileBytes =   Files.readAllBytes(new File(filePath + name).toPath());
        BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(filePath + name));
        // 获取响应的输出流
        BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

        response.setContentType("image/*"); // 告知前端，响应的是图片

        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, len);
            outputStream.flush();
        }
        //关闭资源
        outputStream.close();
        fileInputStream.close();
    }

    /**
     * 文件下载的方法2：用springboot的 ResponseEntity
     *
     * @param
     * @return
     * @author: LiBingYan
     * @时间: 2022/9/30
     */
    @GetMapping(value = "/downfile")
    public ResponseEntity<ByteArrayResource> downloadFile(String name, HttpServletResponse response) throws IOException {
        byte[] bytes = Files.readAllBytes(new File(filePath + name).toPath());
        ByteArrayResource bar = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=test2.png")
                .body(bar);
    }
}
