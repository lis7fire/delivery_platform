package com.bing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bing.entity.SetmealDish;

/**
 * 套餐菜品关系(SetmealDish)表服务接口
 *
 * @author makejava
 * @since 2022-10-08 22:31:23
 */
public interface SetmealDishService extends IService<SetmealDish> {
    // 批量删除 某个套餐 下面的菜品
    void removeBatchBySetmealId(Long SetmealId);
}
