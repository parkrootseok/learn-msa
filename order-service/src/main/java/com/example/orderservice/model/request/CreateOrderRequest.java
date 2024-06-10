package com.example.orderservice.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Data;

@Data
public class CreateOrderRequest {

    private String productId;
    private Integer quantity;
    private Integer price;

}
