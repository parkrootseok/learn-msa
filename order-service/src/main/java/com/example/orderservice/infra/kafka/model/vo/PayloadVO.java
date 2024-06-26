package com.example.orderservice.infra.kafka.model.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PayloadVO {

    private String order_id;
    private String user_id;
    private String product_id;
    private int quantity;
    private int price;
    private int total_price;

    @Builder
    public PayloadVO(String orderId, String userId, String productId, int quantity, int price,
            int totalPrice) {
        this.order_id = orderId;
        this.user_id = userId;
        this.product_id = productId;
        this.quantity = quantity;
        this.price = price;
        this.total_price = totalPrice;
    }

}
