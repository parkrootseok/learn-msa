package com.example.orderservice.domain.model.dto;

import lombok.Data;

@Data
public class OrderDto {

    private String orderId;
    private String productId;
    private String userId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

}
