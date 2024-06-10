package com.example.orderservice.controller;

import com.example.orderservice.model.dto.OrderDto;
import com.example.orderservice.model.entity.Order;
import com.example.orderservice.model.request.CreateOrderRequest;
import com.example.orderservice.model.response.CreateOrderResponse;
import com.example.orderservice.model.response.GetOrderResponse;
import com.example.orderservice.service.OrderService;
import java.util.ArrayList;
import java.util.List;
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

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(createOrderRequest, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto createOrder = orderService.createOrder(orderDto);
        CreateOrderResponse response = mapper.map(createOrder, CreateOrderResponse.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<GetOrderResponse>> getOrders(@PathVariable("userId") String userId) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Iterable<Order> orders = orderService.getOrdersByUserId(userId);

        List<GetOrderResponse> response = new ArrayList<>();
        orders.forEach(
                order -> response.add(mapper.map(order, GetOrderResponse.class))
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

}
