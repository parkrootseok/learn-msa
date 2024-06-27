package com.example.userservice.global.error;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler({FeignException.class})
    protected ResponseEntity handleFeignException(FeignException exception) {

        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.valueOf(exception.status())
        );

    }


}
