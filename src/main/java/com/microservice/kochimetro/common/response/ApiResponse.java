package com.microservice.kochimetro.common.response;

import lombok.*;

import java.time.Instant;

//this class is immutable therefore final
/*
 * LocalDateTime stores date and time without timezone information. we don't know is it india, new york etc
 * Instant represents an exact moment in UTC and is ideal for timestamps
 * like createdAt, updatedAt, logs, and auditing.
 */
@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data; //one class infinite reuse
    private final Instant timestamp;
}
