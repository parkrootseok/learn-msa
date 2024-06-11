package com.example.userservice.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserRequest {

    @NotNull(message = "Email can't be null")
    @Size(min = 2)
    private String email;

    @NotNull(message = "Password can't be null")
    @Size(min = 8)
    private String password;

}
