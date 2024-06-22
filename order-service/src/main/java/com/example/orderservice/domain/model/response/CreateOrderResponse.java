package com.example.orderservice.domain.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CreateOrderResponse {

    private String orderId;
    private String productId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;
    private Date createAt;

}
