package com.cihantech.userservice.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
@Builder
@Data
public class ValidationException {
    private Map<String, String>  validationErrors;
    private int statusCode;
    private String exceptionType;
    private LocalDateTime errorTime;
}