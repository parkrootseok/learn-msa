package com.example.userservice.model.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class GreetingVO {

    @Value("${greeting.message}")
    private String message;

}
