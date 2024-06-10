package com.example.orderservice.exception;

import com.example.orderservice.error.ErrorCode;

public class NotExistsOrderException extends BaseException {

    public NotExistsOrderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
