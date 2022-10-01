package com.bing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bing.dao.DishFlavorDao;
import com.bing.entity.DishFlavorDO;
import com.bing.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * 菜品口味关系表(DishFlavor)表服务实现类
 *
 * @author makejava
 * @since 2022-09-30 11:01:50
 */
@Service("dishFlavorService")
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorDao, DishFlavorDO> implements DishFlavorService {

}

