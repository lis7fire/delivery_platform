package com.bing.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime create_time; //     '创建时间',
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime update_time; //     '更新时间',

    @TableField(fill = FieldFill.INSERT)
    private Long create_user; //   '创建人',
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long update_user; //   '修改人',

}
