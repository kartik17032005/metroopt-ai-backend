package com.microservice.kochimetro.mileage.dto.response;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MileageRecordResponse {
    private UUID id;
    private UUID trainId;
    private String trainNumber;
    private Integer mileage;
    private Instant recordedAt;
    private String recordedBy;
    private Instant createdAt;
    private Instant updatedAt;
}
