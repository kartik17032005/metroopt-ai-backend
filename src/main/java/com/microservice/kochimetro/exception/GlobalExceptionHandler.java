package com.microservice.kochimetro.exception;

import com.microservice.kochimetro.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice // whenever any exception will occur this class will be checked first
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)//"If a ResourceNotFoundException is thrown anywhere in a controller, invoke this method."
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        String message = resourceNotFoundException.getMessage();
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message(message)
                .success(false)
                .data(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiResponse);
    }

    @ExceptionHandler(BadRequestException.class)//registers this class
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException badRequestException){
        String message = badRequestException.getMessage();
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message(message)
                .success(false)
                .data(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)//registers this class
    public ResponseEntity<ApiResponse<Object>> handleUnauthorizedException(UnauthorizedException unauthorizedException){
        String message = unauthorizedException.getMessage();
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message(message)
                .success(false)
                .data(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(apiResponse);
    }
}
