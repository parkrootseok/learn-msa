package com.example.orderservice.service;

import com.example.orderservice.model.dto.OrderDto;
import com.example.orderservice.model.entity.Order;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<Order> getOrdersByUserId(String userId);

}
