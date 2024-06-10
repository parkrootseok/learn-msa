package com.example.orderservice.exception;

import com.example.orderservice.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;


}
