package com.example.catalogservice.domain.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCatalogResponse {

    private String productId;
    private String name;
    private Integer price;
    private Integer stock;
    private Date createdAt;

}
