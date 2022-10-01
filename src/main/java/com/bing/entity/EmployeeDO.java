package com.bing.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("employee")
public class EmployeeDO {
    @TableId(value = "id")
    private Long employeeID; // '主键'
    private String name; // '姓名',
    private String username; // '用户名',
    private String password; // '密码',
    private String phone; // '手机号',
    private String sex; // '性别',
    @TableField(value = "id_number")
    private String idNumber; // '身份证号',
    private int status; //   '状态 0:禁用，1:正常',

    //创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //创建人
    @TableField("create_user")
    private Long createUser;
    //修改人
    @TableField("update_user")
    private Long updateUser;

}
