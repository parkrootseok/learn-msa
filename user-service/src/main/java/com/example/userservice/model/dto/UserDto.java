package com.example.userservice.model.dto;

import com.example.userservice.model.response.GetOrderResponse;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class UserDto {

    private String userId;
    private String email;
    private String password;
    private String name;
    private Date createdAt;
    private String encryptedPassword;
    private List<GetOrderResponse> orders;

}
