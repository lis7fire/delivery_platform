package com.bing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bing.entity.Orders;

/**
 * 订单表(Orders)表服务接口
 *
 * @author makejava
 * @since 2022-10-14 23:29:41
 */
public interface OrdersService extends IService<Orders> {

    public void submit(Orders orderInfo, Long userId);
}

