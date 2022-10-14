package com.bing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bing.dao.SetmealDishDao;
import com.bing.entity.SetmealDish;
import com.bing.service.SetmealDishService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 套餐菜品关系(SetmealDish)表服务实现类
 *
 * @author makejava
 * @since 2022-10-08 22:31:23
 */
@Service("setmealDishService")
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishDao, SetmealDish> implements SetmealDishService {
    @Resource
    private SetmealDishDao setmealDishDao;

    @Override
    public void removeBatchBySetmealId(Long SetmealId) {
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, SetmealId);
        setmealDishDao.delete(queryWrapper);
    }
}
