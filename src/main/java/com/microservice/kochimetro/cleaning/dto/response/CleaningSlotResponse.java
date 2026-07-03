package com.microservice.kochimetro.cleaning.dto.response;

import com.microservice.kochimetro.cleaning.entity.enums.CleaningStatus;
import com.microservice.kochimetro.cleaning.entity.enums.CleaningType;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CleaningSlotResponse {
    private UUID id;
    private UUID trainId;
    private String trainNumber;
    private CleaningType cleaningType;
    private CleaningStatus status;
    private Instant scheduledTime;
    private Instant completedTime;
    private String assignedStaff;
    private Instant createdAt;
    private Instant updatedAt;
}
