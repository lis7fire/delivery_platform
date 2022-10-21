package com.bing.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细表(OrderDetail)表实体类
 *
 * @author makejava
 * @since 2022-10-19 16:33:57
 */
@SuppressWarnings("serial")
@TableName("Order_detail")
@Data
public class OrderDetail extends Model<OrderDetail> {
    //主键
    private Long id;
    //名字
    private String name;
    //图片
    private String image;
    //订单id
    @TableField("order_id")
    private Long orderId;
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
    private BigDecimal amount;

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

