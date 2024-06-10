package com.example.catalogservice.exception;

import com.example.catalogservice.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;


}
