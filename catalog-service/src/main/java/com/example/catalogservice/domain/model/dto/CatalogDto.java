package com.example.catalogservice.domain.model.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class CatalogDto implements Serializable {

    private String userId;
    private String orderId;
    private String productId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

}
