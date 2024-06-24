package com.example.orderservice.domain.service;

import com.example.orderservice.domain.model.dto.OrderDto;
import com.example.orderservice.domain.model.entity.OrderEntity;
import com.example.orderservice.domain.repository.OrderRepository;
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

    /**
     * kafka를 활용하여 DB에 저장하므로 아래 코드는 불필요
     */
//    @Override
//    public OrderDto createOrder(OrderDto orderDto) {
//
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
//        orderDto.setOrderId(UUID.randomUUID().toString());
//        orderDto.setTotalPrice(orderDto.getPrice() * orderDto.getQuantity());
//        orderRepository.save(orderEntity);
//
//        return orderDto;
//
//    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

        return mapper.map(orderEntity, OrderDto.class);

    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

}
