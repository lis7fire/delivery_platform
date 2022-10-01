package com.bing.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜品口味关系表(DishFlavor)表实体类
 *
 * @author makejava
 * @since 2022-09-30 11:01:49
 */
@SuppressWarnings("serial")
@Data
@TableName("dish_flavor")
public class DishFlavorDO extends Model<DishFlavorDO> {
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //菜品
    @TableField("dish_id")
    private Long dishId;
    //口味名称
    private String name;
    //口味数据list
    private String value;
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
    //是否删除
    @TableField("is_deleted")
    private Integer isDeleted;

}

