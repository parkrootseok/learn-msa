package com.example.catalogservice.exception;

import com.example.catalogservice.error.ErrorCode;

public class NotExistsUserException extends BaseException {

    public NotExistsUserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
