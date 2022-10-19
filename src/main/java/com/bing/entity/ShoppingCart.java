package com.bing.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 购物车(ShoppingCart)表实体类
 *
 * @author makejava
 * @since 2022-10-14 23:36:13
 */
@SuppressWarnings("serial")
@TableName("shopping_cart")
@Data
public class ShoppingCart extends Model<ShoppingCart> {
    //主键
    @TableId
    private Long id;
    //名称
    private String name;
    //图片
    private String image;
    //主键
    @TableField("user_id")
    private Long userId;
    //菜品id
    @TableField("dish_id")
    private Long dishId;
    //套餐id
    @TableField("setmeal_id")
    private Long setmealId;
    //口味
    @TableField("dish_flavor")
    private String dishFlavor;
    //数量
    private Integer number;
    //金额
    private Double amount;
    //创建时间
    @TableField("create_time")
    private LocalDateTime createTime;


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

