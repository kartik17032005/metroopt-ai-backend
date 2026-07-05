package com.microservice.kochimetro.exception;

import com.microservice.kochimetro.train.entity.enums.Depot;

import java.util.UUID;

// 2 choices
//first -> to extend Exception (checked Exception) that makes us handle the exceptions
//using try cache and another one is RuntimeException (unchecked)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String depotResource, String id, UUID id1) {
    }

    public ResourceNotFoundException(String depotResource, String depot, Depot depot1) {
    }
}
