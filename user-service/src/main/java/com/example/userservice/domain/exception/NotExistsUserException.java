package com.example.userservice.domain.exception;

import com.example.userservice.global.error.ErrorCode;
import com.example.userservice.global.exception.BaseException;

public class NotExistsUserException extends BaseException {

    public NotExistsUserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
