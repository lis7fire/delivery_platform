package com.bing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bing.entity.DTO.SetmealDTO;
import com.bing.entity.SetmealDO;

import java.util.List;

/**
 * 套餐(Setmeal)表服务接口
 *
 * @author makejava
 * @since 2022-10-01 22:54:40
 */
public interface SetmealService extends IService<SetmealDO> {


    /**
     * 新增 / 修改 套餐，同时需要保存套餐和菜品的关联关系；
     * 修改套餐，同时需要删除套餐和菜品的关联数据
     *
     * @param setmealDto
     */

    void editOrSaveWithDish(boolean isSave, SetmealDTO setmeal);

    /**
     * [数据库的外键自动操作]删除套餐，同时需要删除套餐和菜品的关联数据
     *
     * @param ids
     */
    void removeWithDish(List<Long> ids);

    /**
     * 批量修改套餐状态，单个也行
     *
     * @param newStatus 新状态
     * @param ids       套餐ID列表
     * @return
     */
    public int editBatchStatus(int newStatus, List<Long> ids);

}

