package com.bing.entity.VO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜品管理(Dish)表实体类
 *
 * @author makejava
 * @since 2022-09-26 22:23:52
 */
@Data
public class DishVO {
    //主键
    private Long id;
    //菜品名称
    private String name;
    //菜品分类id
    private Long categoryId;
    //菜品价格
    private Double price;
    //商品码
    private String code;
    //图片
    private String image;
    //描述信息
    private String description;
    //0 停售 1 起售
    private Integer status;
    //顺序
    private Integer sort;

    private LocalDateTime create_time; //     '创建时间',
    private LocalDateTime update_time; //     '更新时间',

    private Long create_user; //   '创建人',
    private Long update_user; //   '修改人',

    //是否删除,1删除
    private Integer isDeleted;

}

