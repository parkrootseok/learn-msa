package com.example.orderservice.domain.exception;

import com.example.orderservice.global.error.ErrorCode;
import com.example.orderservice.global.exception.BaseException;

public class NotExistsOrderException extends BaseException {

    public NotExistsOrderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
