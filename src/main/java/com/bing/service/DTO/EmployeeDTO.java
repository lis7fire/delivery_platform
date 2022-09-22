package com.bing.service.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
//复制于 DO
public class EmployeeDTO {
    private Long employeeID; // '主键'
    private String name; // '姓名',
    private String username; // '用户名',
    private String password; // '密码',
    private String phone; // '手机号',
    private String sex; // '性别',
    private String idNumber; // '身份证号',
    private int status; //   '状态 0:禁用，1:正常',

    private Long create_user; //   '创建人',
    private Long update_user; //   '修改人',

// DO才有
//    private LocalDateTime create_time; //     '创建时间',
//    private LocalDateTime update_time; //     '更新时间',

}
