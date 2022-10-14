package com.bing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 套餐菜品关系(SetmealDish)实体类
 *
 * @author makejava
 * @since 2022-10-08 22:31:23
 */
@Data
@TableName("setmeal_dish")
public class SetmealDish implements Serializable {
    private static final long serialVersionUID = -99464451370546586L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 套餐id
     */
    @TableField("setmeal_id")
    private Long setmealId;
    /**
     * 菜品id
     */
    @TableField("dish_id")
    private Long dishId;
    /**
     * 菜品名称 （冗余字段）
     */
    private String name;
    /**
     * 菜品原价（冗余字段）
     */
    private Double price;
    /**
     * 份数
     */
    private Integer copies;
    /**
     * 排序
     */
    private Integer sort;


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
    //是否删除；1删除
    @TableField("is_deleted")
    private Integer isDeleted;

}

