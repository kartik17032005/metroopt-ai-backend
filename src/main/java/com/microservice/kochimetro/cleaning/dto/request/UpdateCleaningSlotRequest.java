package com.microservice.kochimetro.cleaning.dto.request;

import com.microservice.kochimetro.cleaning.entity.enums.CleaningStatus;
import com.microservice.kochimetro.cleaning.entity.enums.CleaningType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCleaningSlotRequest {

    @NotNull(message = "Cleaning type is required")
    private CleaningType cleaningType;

    @NotNull(message = "Cleaning status is required")
    private CleaningStatus status;

    @NotNull(message = "Scheduled time is required")
    private Instant scheduledTime;

    private Instant completedTime;

    @NotBlank(message = "Assigned staff is required")
    private String assignedStaff;
}
