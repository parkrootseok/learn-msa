package com.example.userservice.domain.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserResponse {

    private String userId;
    private String email;
    private String name;
    private List<GetOrderResponse> orders;

}
