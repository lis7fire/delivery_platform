package com.bing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bing.dao.SetmealDishDao;
import com.bing.entity.DTO.SetmealDTO;
import com.bing.entity.SetmealDO;
import com.bing.entity.SetmealDish;
import com.bing.mapper.SetmealDao;
import com.bing.service.SetmealDishService;
import com.bing.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐(Setmeal)表服务实现类
 *
 * @author makejava
 * @since 2022-10-01 22:54:40
 */
@Service("setmealService")
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, SetmealDO> implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional
    public void editOrSaveWithDish(boolean isSave, SetmealDTO setmealDto) {
        System.out.println(setmealDto);

        if (isSave) {// 新增时，先插入主表，以便MP生成套餐id，然后再插关系表
            setmealDao.insert(setmealDto);
        } else {// 修改时的DB操作
            setmealDao.updateById(setmealDto); // 先更新主表套餐表，再删除关系表中的相应行

            setmealDishService.removeBatchBySetmealId(setmealDto.getId());
        }

        // 循环 菜品列表，设置套餐id及修改人 等
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList = setmealDishList.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            item.setUpdateUser(setmealDto.getUpdateUser());
            item.setCreateUser(setmealDto.getUpdateUser());
            return item;
        }).collect(Collectors.toList());

// 最后插入关系表
        setmealDishService.saveBatch(setmealDishList);

    }

    @Override
    public void removeWithDish(List<Long> ids) {

    }

    @Override
    public int editBatchStatus(int newStatus, List<Long> ids) {
        return setmealDao.updateBatchStatus(newStatus, ids);
    }

}

