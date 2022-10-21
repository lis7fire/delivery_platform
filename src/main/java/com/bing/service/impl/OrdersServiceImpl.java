package com.bing.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bing.common.CustomException;
import com.bing.dao.OrdersDao;
import com.bing.entity.AddressBook;
import com.bing.entity.OrderDetail;
import com.bing.entity.Orders;
import com.bing.entity.ShoppingCart;
import com.bing.service.AddressBookService;
import com.bing.service.OrderDetailService;
import com.bing.service.OrdersService;
import com.bing.service.ShoppingCartService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 订单表(Orders)表服务实现类
 *
 * @author makejava
 * @since 2022-10-14 23:29:41
 */
@Service("ordersService")
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private AddressBookService addressBookService;

    @Override
    @Transactional
    public void submit(Orders orderInfo, Long userId) {
// 获取购物车里面所有记录
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.listByMap(map);

        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("购物车为空，无法下单。");
        }
//        查询用户数据

//        验证地址数据
        Long addressId = orderInfo.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressId);
        if (null == addressBook || !addressBook.getUserId().equals(userId)) {
            throw new CustomException("用户地址不匹配，无法下单。");
        }
//      用 MP 生成 订单id
        Long orderId = IdWorker.getId();

//        验证订单总金额 及 商品数量 ，原子操作
        AtomicInteger sum = new AtomicInteger(0);
        AtomicInteger num = new AtomicInteger();
// 相关购物车表的行-移动到订单详情表中（多行，菜品信息等）
        List<OrderDetail> orderDetails = shoppingCartList.stream().map((item) -> {
            OrderDetail oneDetail = new OrderDetail();
            MyBeanUtil.copyProperties(item, oneDetail);
            oneDetail.setOrderId(orderId);
            num.addAndGet(item.getNumber());
            sum.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());  //item.getAmount()* item.getNumber();
            return oneDetail;
        }).collect(Collectors.toList());

//添加对应订单表数据（一行，支付、状态等）
        orderInfo.setId(orderId);
        orderInfo.setOrderTime(LocalDateTime.now());
        orderInfo.setStatus(2);
        orderInfo.setAmount(new BigDecimal(sum.get()));  //      订单总金额
        orderInfo.setUserId(userId);
        orderInfo.setNumber(num.intValue());// 订单中商品件数
        orderInfo.setConsignee(addressBook.getConsignee());
        orderInfo.setPhone(addressBook.getPhone());
        orderInfo.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

// 写入一个订单数据到订单表
        this.save(orderInfo);
//订单详情中批量插入-原来购物车中的数据
        orderDetailService.saveBatch(orderDetails);
//购物车对应数据清空
        boolean removes = shoppingCartService.removeByMap(map);
        log.info("成功将购物车移动到订单详情表");
    }
}

