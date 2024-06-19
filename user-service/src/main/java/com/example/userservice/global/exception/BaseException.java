package com.example.userservice.global.exception;

import com.example.userservice.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;


}
