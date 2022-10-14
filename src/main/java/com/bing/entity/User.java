package com.bing.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息(User)表实体类
 *
 * @author makejava
 * @since 2022-10-12 22:05:17
 */
@Data
@SuppressWarnings("serial")
public class User extends Model<User> {
    //主键
    @TableId
    private Long id;
    //姓名
    private String name;
    //手机号
    private String phone;
    //性别
    private String sex;
    //身份证号
    @TableField("id_number")
    private String idNumber;
    //头像
    private String avatar;
    //状态 0:禁用，1:正常
    private Integer status;



    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

