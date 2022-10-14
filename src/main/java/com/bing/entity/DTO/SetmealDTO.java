package com.bing.entity.DTO;


import com.bing.entity.SetmealDO;
import com.bing.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * 套餐(Setmeal) DTO传输类，
 *
 * @author makejava
 * @since 2022-09-27 08:44:51
 */
@Data
public class SetmealDTO extends SetmealDO {

    //    套餐内菜品列表
    private List<SetmealDish> setmealDishes;
    //套餐种类
    private String categoryName;

}

