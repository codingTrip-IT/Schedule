package com.example.schedule.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handleApplicationException(ApplicationException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", ex.getErrorMessageCode().getCode());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("errors", ex.getErrors());
        errorResponse.put("timestamp", LocalDateTime.now());

        HttpStatus status;
        switch (ex.getErrorMessageCode()) {
            case BAD_REQUEST:
                status = HttpStatus.BAD_REQUEST;
                break;
            case UNAUTHORIZED:
                status = HttpStatus.UNAUTHORIZED;
                break;
            case FORBIDDEN:
                status = HttpStatus.FORBIDDEN;
                break;
            case NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                break;
            case INTERNAL_SERVER_ERROR:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(errorResponse);
    }
}
