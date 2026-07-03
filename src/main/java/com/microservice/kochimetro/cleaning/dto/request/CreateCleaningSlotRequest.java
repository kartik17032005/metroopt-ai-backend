package com.microservice.kochimetro.cleaning.dto.request;

import com.microservice.kochimetro.cleaning.entity.enums.CleaningType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCleaningSlotRequest {

    @NotNull(message = "Train ID is required")
    private UUID trainId;

    @NotNull(message = "Cleaning type is required")
    private CleaningType cleaningType;

    @NotNull(message = "Scheduled time is required")
    private Instant scheduledTime;

    @NotBlank(message = "Assigned staff is required")
    private String assignedStaff;
}
