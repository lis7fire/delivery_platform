package com.bing.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 地址管理(AddressBook)表实体类
 *
 * @author makejava
 * @since 2022-10-17 10:43:40
 */
@SuppressWarnings("serial")
@Data
public class AddressBook extends Model<AddressBook> {
    //主键
    private Long id;
    //用户id
    @TableField("user_id")
    private Long userId;
    //收货人
    private String consignee;
    //性别 0 女 1 男
    private Integer sex;
    //手机号
    private String phone;
    //省级区划编号
    @TableField("province_code")
    private String provinceCode;
    //省级名称
    @TableField("province_name")
    private String provinceName;
    //市级区划编号
    @TableField("city_code")
    private String cityCode;
    //市级名称
    @TableField("city_name")
    private String cityName;
    //区级区划编号
    @TableField("district_code")
    private String districtCode;
    //区级名称
    @TableField("district_name")
    private String districtName;
    //详细地址
    private String detail;
    //标签
    private String label;
    //默认 0 否 1是
    @TableField("is_default")
    private Integer isDefault;

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

    //是否删除,1删除
//    @TableLogic(delval = "1", value = "0")
    @TableField(value = "is_deleted")
    private Integer isDeleted;


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

