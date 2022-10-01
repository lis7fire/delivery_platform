package com.bing.entity.VO;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 套餐(Setmeal)表实体类
 *
 * @author makejava
 * @since 2022-09-27 08:44:51
 */
@Data
public class SetmealVO extends PageRequestParamsVO{
    //主键
    private Long id;
    //菜品分类id
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

    private LocalDateTime createTime; //     '创建时间',
    private LocalDateTime updateTime; //     '更新时间',

    private Long createUser; //   '创建人',
    private Long updateUser; //   '修改人',
    //是否删除；1删除
    private Integer isDeleted;
}

