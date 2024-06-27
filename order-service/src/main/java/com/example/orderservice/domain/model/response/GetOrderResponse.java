package com.example.orderservice.domain.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOrderResponse {

    private String orderId;
    private String productId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;
    private Date createdAt;

}
