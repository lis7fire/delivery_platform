package com.bing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.common.ConstArgs;
import com.bing.common.R;
import com.bing.entity.DTO.OrderDto;
import com.bing.entity.OrderDetail;
import com.bing.entity.Orders;
import com.bing.entity.VO.PageRequestParamsVO;
import com.bing.service.OrderDetailService;
import com.bing.service.OrdersService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/userPage")
    public R<IPage<Orders>> getOrders(HttpSession session, PageRequestParamsVO pageParams) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId)
                .orderByDesc(Orders::getOrderTime);
        IPage<Orders> ordersPage = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        orderService.page(ordersPage, queryWrapper);

        ordersPage.convert((item) -> {
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("order_id", item.getId());
            List<OrderDetail> orderDetails = orderDetailService.listByMap(map);

//            这里将菜品信息放入订单中，发往前台
            OrderDto orderDto = new OrderDto();
            MyBeanUtil.copyProperties(item, orderDto);
            orderDto.setOrderDetails(orderDetails);
            return orderDto;
        });

//        List<OrderDto> orderDtoList = new ArrayList<>();
//        List<Orders> ordersList = ordersPage.getRecords();
//        for (Orders o : ordersList) {
//            Map<String, Object> map = new HashMap<String, Object>(1);
//            map.put("order_id", o.getId());
//            List<OrderDetail> orderDetails = orderDetailService.listByMap(map);
//
////            这里将菜品信息放入订单中，发往前台
//            OrderDto orderDto = new OrderDto();
//            MyBeanUtil.copyProperties(o, orderDto);
//            orderDto.setOrderDetails(orderDetails);
//            orderDtoList.add(orderDto);
//        }

        return R.success(ordersPage);
    }

    //    由购物车-去结算-添加订单
    @PostMapping("/submit")
    public R<String> add(HttpSession session, @RequestBody Orders orderInfo) {
        log.info("订单信息：{}", orderInfo);
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);

        orderService.submit(orderInfo, userId);

        return R.success("下单成功");
    }

}
