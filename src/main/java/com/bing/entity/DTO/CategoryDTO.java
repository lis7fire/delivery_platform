package com.bing.entity.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class CategoryDTO implements Serializable {
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

    private LocalDateTime createTime; //     '创建时间',
    private LocalDateTime updateTime; //     '更新时间',

    private Long createUser; //   '创建人',
    private Long updateUser; //   '修改人',
}

