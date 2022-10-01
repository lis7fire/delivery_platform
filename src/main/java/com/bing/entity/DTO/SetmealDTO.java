package com.bing.entity.DTO;


import lombok.Data;

import java.util.Date;

/**
 * 套餐(Setmeal)表实体类
 *
 * @author makejava
 * @since 2022-09-27 08:44:51
 */
@Data
public class SetmealDTO {
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

    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //创建人
    private Long createUser;
    //修改人
    private Long updateUser;
    //是否删除；1删除
    private Integer isDeleted;

}

