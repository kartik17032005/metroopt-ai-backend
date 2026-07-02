package com.microservice.kochimetro.exception;

// 2 choices
//first -> to extend Exception (checked Exception) that makes us handle the exceptions
//using try cache and another one is RuntimeException (unchecked)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
