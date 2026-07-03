package com.microservice.kochimetro.jobCard.dto.request;

import com.microservice.kochimetro.jobCard.entity.enums.JobPriority;
import com.microservice.kochimetro.jobCard.entity.enums.JobStatus;
import com.microservice.kochimetro.jobCard.entity.enums.MaintenanceType;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobCardRequest {

    private String title;
    private String description;
    private MaintenanceType maintenanceType;
    private JobPriority priority;
    private String assignedEngineer;
    private JobStatus jobStatus;
    private Instant estimatedCompletion;

}
