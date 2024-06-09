package com.example.userservice.model.dto;

import java.util.Date;
import lombok.Data;

@Data
public class UserDto {

    private String userId;
    private String email;
    private String password;
    private String name;
    private Date createdAt;
    private String encryptedPassword;

}
