package com.example.orderservice.service;

import com.example.orderservice.model.dto.OrderDto;
import com.example.orderservice.model.entity.OrderEntity;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);

}
