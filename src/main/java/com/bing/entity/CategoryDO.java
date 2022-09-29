package com.bing.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品及套餐分类(Category)实体类
 *
 * @author makejava
 * @since 2022-09-26 22:12:48
 */
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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime create_time; //     '创建时间',
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime update_time; //     '更新时间',

    private Long create_user; //   '创建人',
    private Long update_user; //   '修改人',


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public Long getCreate_user() {
        return create_user;
    }

    public void setCreate_user(Long create_user) {
        this.create_user = create_user;
    }

    public Long getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(Long update_user) {
        this.update_user = update_user;
    }
}

