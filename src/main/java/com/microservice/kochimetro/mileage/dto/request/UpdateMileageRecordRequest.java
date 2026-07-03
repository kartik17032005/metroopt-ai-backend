package com.microservice.kochimetro.mileage.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMileageRecordRequest {

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage must be non-negative")
    private Integer mileage;

    @NotNull(message = "Record date is required")
    private Instant recordedAt;

    @NotBlank(message = "Recorder name is required")
    private String recordedBy;
}
