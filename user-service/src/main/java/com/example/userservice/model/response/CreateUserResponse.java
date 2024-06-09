package com.example.userservice.model.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserResponse {

    private String userId;
    private String email;
    private String name;

}
