package com.microservice.kochimetro.jobCard.dto.response;

import com.microservice.kochimetro.jobCard.entity.enums.JobPriority;
import com.microservice.kochimetro.jobCard.entity.enums.JobStatus;
import com.microservice.kochimetro.jobCard.entity.enums.MaintenanceType;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCardResponse {

    private UUID id;

    private UUID trainId;

    private String trainNumber;

    private String title;

    private String description;

    private MaintenanceType maintenanceType;

    private JobPriority priority;

    private JobStatus jobStatus;

    private String assignedEngineer;

    private Instant estimatedCompletion;

    private Instant createdAt;

    private Instant updatedAt;
}
