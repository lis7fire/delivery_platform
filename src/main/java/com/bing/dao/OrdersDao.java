package com.bing.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.bing.entity.Orders;

/**
 * 订单表(Orders)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-14 23:29:40
 */
public interface OrdersDao extends BaseMapper<Orders> {

}

