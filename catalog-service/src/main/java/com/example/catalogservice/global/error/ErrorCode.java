package com.example.catalogservice.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_EXISTS_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");

    private HttpStatus httpStatus;
    private String message;

}
