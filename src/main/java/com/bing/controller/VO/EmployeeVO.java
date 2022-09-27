package com.bing.controller.VO;

import com.alibaba.druid.sql.visitor.functions.ToDate;
import com.bing.common.LocalDateTimeToStringConverter;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
/**
 * 原则： DTO 是VO或者DO的子集， VO是包含页面的所有字段的类。DO与DB的字段完全对应。DO与VO无直接相关。通过DIO间接相关
 * 注意： 前端的字段与这个的属性映射必须全字匹配，对大小写敏感，
 *       与前端联调试，必须使用Chrome的无痕模式测试 或者 F12-网络-停用缓存，否则 html 的缓存不刷新，无法测试。
 * @author: LiBingYan
 * @时间: 2022/9/22
 */

// 复制于 DO
public class EmployeeVO {
    // DTO一致
    //防止后端Long类型 到前端 JSON数据 精度丢失 ，前端是string存储的，所以到后端不会有精度丢失
//    @JsonSerialize(using = ToStringSerializer.class)  // 用MVC配置消息转换器 替代了
    private Long employeeID; // '主键'
    private String name; // '姓名',
    private String username; // '用户名',
    private String phone; // '手机号',
    private String sex; // '性别',
    private String idNumber; // '身份证号',
    private int status; //   '状态 0:禁用，1:正常',
    // 比DTO多的
    private Long create_user; //   '创建人',
    private Long update_user; //   '修改人',
    //    @JsonSerialize(using = LocalDateTimeToStringConverter.class)
    private LocalDateTime create_time; //     '创建时间',
    private LocalDateTime update_time; //     '更新时间',

    // 只在 DO中
    //    private String password; // '密码',
}

