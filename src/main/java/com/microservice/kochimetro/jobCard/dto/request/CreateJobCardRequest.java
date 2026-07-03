package com.microservice.kochimetro.jobCard.dto.request;

import com.microservice.kochimetro.jobCard.entity.enums.JobPriority;
import com.microservice.kochimetro.jobCard.entity.enums.MaintenanceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateJobCardRequest {

    @NotNull(message = "Train ID is required")
    private UUID trainId;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Maintenance type is required")
    private MaintenanceType maintenanceType;

    @NotNull(message = "Priority is required")
    private JobPriority priority;

    @NotBlank(message = "Assigned engineer is required")
    private String assignedEngineer;

    private Instant estimatedCompletion;
}