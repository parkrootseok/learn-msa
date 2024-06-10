package com.example.orderservice.service;

import com.example.orderservice.model.dto.OrderDto;
import com.example.orderservice.model.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getPrice() * orderDto.getQuantity());
        Order order = mapper.map(orderDto, Order.class);

        orderRepository.save(order);

        return mapper.map(order, OrderDto.class);

    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Order order = orderRepository.findByOrderId(orderId);

        return mapper.map(order, OrderDto.class);

    }

    @Override
    public Iterable<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

}
