package com.ecommerce.orderservice.exception;

import org.springframework.http.HttpStatus;

public class CannotCancelOrderException extends ApiException{
    public CannotCancelOrderException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
