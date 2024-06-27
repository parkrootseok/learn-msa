package com.example.apigatewayservice.global.constants.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * [ UNAUTHORIZED, 404 ]
     */
    NOT_CONTAIN_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "권한 정보가 포함되어 있지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    /**
     * [ NOT FOUND, 404 ]
     */
    NOT_EXISTS_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");

    private HttpStatus httpStatus;
    private String message;

}
