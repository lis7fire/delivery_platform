package com.bing.controller.VO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
// 复制于 DTO
// 前端的字段与这个的属性映射必须全字匹配，对大小写敏感，
// 与前端联调试，必须使用Chrome的无痕模式测试，否则 html 的缓存不刷新，无法测试。
public class EmployeeVO {

    private Long employeeID; // '主键'
    private String Name; // '姓名',
    private String username; // '用户名',
    //    private String password; // '密码',
    private String phone; // '手机号',
    private String sex; // '性别',
    private String id_number; // '身份证号',
    private int status; //   '状态 0:禁用，1:正常',

//    private LocalDateTime create_time; //     '创建时间',
//    private LocalDateTime update_time; //     '更新时间',
}
