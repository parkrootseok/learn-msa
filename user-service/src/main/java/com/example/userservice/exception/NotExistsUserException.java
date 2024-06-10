package com.example.userservice.exception;

import com.example.userservice.error.ErrorCode;

public class NotExistsUserException extends BaseException {

    public NotExistsUserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
