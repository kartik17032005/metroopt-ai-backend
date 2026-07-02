package com.microservice.kochimetro.common.response;

import lombok.*;

import java.time.Instant;

/**
 * @param data one class infinite reuse
 */ //this class is immutable therefore final
/*
 * LocalDateTime stores date and time without timezone information. we don't know is it india, new york etc
 * Instant represents an exact moment in UTC and is ideal for timestamps
 * like createdAt, updatedAt, logs, and auditing.
 */
@Builder
public record ApiResponse<T>(boolean success, String message, T data, Instant timestamp) {
}
