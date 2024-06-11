package com.example.userservice.domain.exception;

import com.example.userservice.common.exception.BaseException;
import com.example.userservice.common.error.ErrorCode;

public class NotExistsUserException extends BaseException {

    public NotExistsUserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
