package com.cihantech.orderservice.exception;

import org.springframework.http.HttpStatus;

public class OrderCreationFailedException extends ApiException {

    public OrderCreationFailedException(String message) {
        super(message,HttpStatus.BAD_REQUEST);
    }
}
