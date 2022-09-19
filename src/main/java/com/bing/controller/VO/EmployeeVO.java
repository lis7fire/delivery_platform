package com.bing.controller.VO;

import lombok.Data;

@Data
// 复制于 DTO
public class EmployeeVO {
    private Long employeeID; // '主键'
    private String name; // '姓名',
    private String username; // '用户名',
//    private String password; // '密码',
    private String phone; // '手机号',
    private String sex; // '性别',
    private String id_number; // '身份证号',
//    private int status; //   '状态 0:禁用，1:正常',
}
