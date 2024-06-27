package com.example.apigatewayservice.global.exception;

import com.example.apigatewayservice.global.constants.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;


}
