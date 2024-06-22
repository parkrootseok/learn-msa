package com.example.catalogservice.global.exception;

import com.example.catalogservice.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;


}
