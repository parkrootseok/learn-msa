package com.example.userservice.domain.model.response;

import java.util.Date;
import lombok.Data;

@Data
public class GetOrderResponse {

    private String orderId;
    private String productId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;
    private Date createdAt;

}
