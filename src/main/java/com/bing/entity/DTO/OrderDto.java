package com.bing.entity.DTO;

import com.bing.entity.OrderDetail;
import com.bing.entity.Orders;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 输出给前端的订单表，包含详情
@Data
public class OrderDto extends Orders {
    List<OrderDetail> orderDetails = new ArrayList<>();

}
