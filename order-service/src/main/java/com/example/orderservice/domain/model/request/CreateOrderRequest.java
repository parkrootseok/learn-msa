package com.example.orderservice.domain.model.request;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private String productId;
    private Integer quantity;
    private Integer price;

}
