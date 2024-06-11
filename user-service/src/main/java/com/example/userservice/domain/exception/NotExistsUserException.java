package com.example.userservice.domain.exception;

import com.example.userservice.common.constants.error.ErrorCode;
import com.example.userservice.common.exception.BaseException;

public class NotExistsUserException extends BaseException {

    public NotExistsUserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
