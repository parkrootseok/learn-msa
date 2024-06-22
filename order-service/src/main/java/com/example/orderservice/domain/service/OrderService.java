package com.example.orderservice.domain.service;

import com.example.orderservice.domain.model.dto.OrderDto;
import com.example.orderservice.domain.model.entity.OrderEntity;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);

}
