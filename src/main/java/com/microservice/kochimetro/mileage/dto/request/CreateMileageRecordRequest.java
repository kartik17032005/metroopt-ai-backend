package com.microservice.kochimetro.mileage.dto.request;

import jakarta.validation.constraints.Min;
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
public class CreateMileageRecordRequest {

    @NotNull(message = "Train ID is required")
    private UUID trainId;

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage must be non-negative")
    private Integer mileage;

    @NotNull(message = "Record date is required")
    private Instant recordedAt;

    @NotBlank(message = "Recorder name is required")
    private String recordedBy;
}
