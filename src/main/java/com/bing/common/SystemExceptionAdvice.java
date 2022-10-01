package com.bing.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice(basePackages = "com.bing.controller")
@Slf4j
@Order(value = 2)
public class SystemExceptionAdvice {
    /**
     * 专门用来处理系统异常，主要是 文件上传下载时的IO异常
     * @param
     * @return
     *
     * @author: LiBingYan
     * @时间:    2022/9/30
     */
    @ExceptionHandler(value = IOException.class)
    public R<String> rootExceptionHandler(Exception ex) {
        log.error("发生IO异常错误", ex);
        return R.fail(ExceptionCodeEnum.IO_EXCEPTION);
    }
}
