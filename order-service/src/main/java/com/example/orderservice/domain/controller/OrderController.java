package com.example.orderservice.domain.controller;

import com.example.orderservice.domain.model.dto.OrderDto;
import com.example.orderservice.domain.model.entity.OrderEntity;
import com.example.orderservice.domain.model.request.CreateOrderRequest;
import com.example.orderservice.domain.service.OrderService;
import com.example.orderservice.domain.model.response.CreateOrderResponse;
import com.example.orderservice.domain.model.response.GetOrderResponse;
import com.example.orderservice.infra.kafka.producer.KafkaProducer;
import com.example.orderservice.infra.kafka.producer.OrderProducer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment env;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;
    private final OrderService orderService;

    @GetMapping("/health-check")
    public String status() {

        return String.format("It's working in order service on PORT %s", env.getProperty("local.server.port"));

    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @PathVariable("userId") String userId,
            @RequestBody CreateOrderRequest createOrderRequest
    ) {

        /* create order */
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(createOrderRequest, OrderDto.class);
        orderDto.setUserId(userId);
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getPrice() * orderDto.getQuantity());

        /* send this order to kafka */
        kafkaProducer.send("catalog-topic", orderDto);
        orderProducer.sendCreateOrderMessage("orders", orderDto);

        CreateOrderResponse response = mapper.map(orderDto, CreateOrderResponse.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<GetOrderResponse>> getOrders(@PathVariable("userId") String userId) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);

        List<GetOrderResponse> response = new ArrayList<>();
        orders.forEach(
                orderEntity -> response.add(mapper.map(orderEntity, GetOrderResponse.class))
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

}
