package com.bing.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 套餐(Setmeal)表实体类
 *
 * @author makejava
 * @since 2022-09-27 08:44:51
 */
@Data
@TableName("setmeal")
public class SetmealDO {
    //主键
    private Long id;
    //菜品分类id
    @TableField("category_id")
    private Long categoryId;
    //套餐名称
    private String name;
    //套餐价格
    private Double price;
    //状态 0:停用 1:启用
    private Integer status;
    //编码
    private String code;
    //描述信息
    private String description;
    //图片
    private String image;

    //创建时间
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新时间
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
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

