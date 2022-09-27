package com.bing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bing.mapper.DishDao;
import com.bing.entity.DishDO;
import com.bing.service.DishService;
import org.springframework.stereotype.Service;

/**
 * 菜品管理(Dish)表服务实现类
 *
 * @author makejava
 * @since 2022-09-27 09:31:43
 */
@Service("dishService")
public class DishServiceImpl extends ServiceImpl<DishDao, DishDO> implements DishService {

}

