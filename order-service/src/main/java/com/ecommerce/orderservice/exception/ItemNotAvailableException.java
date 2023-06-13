package com.ecommerce.orderservice.exception;

import org.springframework.http.HttpStatus;

public class ItemNotAvailableException extends ApiException {


    public ItemNotAvailableException(String message ) {
        super(message, HttpStatus.CONFLICT);
    }
}
