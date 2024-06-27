package com.example.userservice.domain.model.response;

import lombok.Data;

@Data
public class CreateUserResponse {

    private String userId;
    private String email;
    private String name;

}
