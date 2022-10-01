package com.bing.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品及套餐分类(Category)实体类
 *
 * @author makejava
 * @since 2022-09-26 22:12:48
 */
@Data
@TableName("category")
public class CategoryDO implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 类型   1 菜品分类 2 套餐分类
     */
    private Integer type;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 顺序
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

}

