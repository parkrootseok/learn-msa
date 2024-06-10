package com.example.orderservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.criteria.CriteriaBuilder.In;
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
    private Date createAt;

}
