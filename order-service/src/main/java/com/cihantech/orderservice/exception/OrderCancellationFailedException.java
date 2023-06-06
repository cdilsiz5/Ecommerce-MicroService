package com.cihantech.orderservice.exception;

import org.springframework.http.HttpStatus;

public class OrderCancellationFailedException extends ApiException{
    public OrderCancellationFailedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}