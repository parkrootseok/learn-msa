package com.example.apigatewayservice.common.exception;

import com.example.apigatewayservice.common.constants.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;


}
