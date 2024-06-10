package com.example.userservice.model.response;

import java.util.Date;
import lombok.Data;

@Data
public class GetOrderResponse {

    private String orderId;
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createAt;

}
