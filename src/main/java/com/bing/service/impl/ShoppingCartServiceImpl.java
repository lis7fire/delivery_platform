package com.bing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bing.dao.ShoppingCartDao;
import com.bing.entity.ShoppingCart;
import com.bing.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * 购物车(ShoppingCart)表服务实现类
 *
 * @author makejava
 * @since 2022-10-14 23:36:14
 */
@Service("shoppingCartService")
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {

}

