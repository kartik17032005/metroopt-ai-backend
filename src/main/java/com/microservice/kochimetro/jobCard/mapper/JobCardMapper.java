package com.microservice.kochimetro.jobCard.mapper;

import com.microservice.kochimetro.jobCard.dto.request.CreateJobCardRequest;
import com.microservice.kochimetro.jobCard.dto.request.UpdateJobCardRequest;
import com.microservice.kochimetro.jobCard.dto.response.JobCardResponse;
import com.microservice.kochimetro.jobCard.entity.JobCard;
import com.microservice.kochimetro.jobCard.entity.enums.JobStatus;
import com.microservice.kochimetro.train.entity.Train;

public class JobCardMapper {
    private JobCardMapper() {
    }

    public static JobCard toEntity(CreateJobCardRequest request, Train train) {
        return JobCard.builder()
                .description(request.getDescription())
                .maintenanceType(request.getMaintenanceType())
                .title(request.getTitle())
                .train(train)
                .jobStatus(JobStatus.OPEN)
                .estimatedCompletion(request.getEstimatedCompletion())
                .assignedEngineer(request.getAssignedEngineer())
                .priority(request.getPriority())
                .build();
    }

    public static JobCardResponse toResponse(JobCard jobCard) {
        return JobCardResponse.builder()
                .id(jobCard.getJobCardId())
                .trainId(jobCard.getTrain().getId())
                .trainNumber(jobCard.getTrain().getTrainNumber())
                .estimatedCompletion(jobCard.getEstimatedCompletion())
                .assignedEngineer(jobCard.getAssignedEngineer())
                .maintenanceType(jobCard.getMaintenanceType())
                .title(jobCard.getTitle())
                .jobStatus(jobCard.getJobStatus())
                .priority(jobCard.getPriority())
                .description(jobCard.getDescription())
                .createdAt(jobCard.getCreatedAt())
                .updatedAt(jobCard.getUpdatedAt())
                .build();
    }

    public static void updateEntity(JobCard jobCard, UpdateJobCardRequest request) {

        if (request.getTitle() != null) {
            jobCard.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            jobCard.setDescription(request.getDescription());
        }

        if (request.getMaintenanceType() != null) {
            jobCard.setMaintenanceType(request.getMaintenanceType());
        }

        if (request.getPriority() != null) {
            jobCard.setPriority(request.getPriority());
        }

        if (request.getJobStatus() != null) {
            jobCard.setJobStatus(request.getJobStatus());
        }

        if (request.getAssignedEngineer() != null) {
            jobCard.setAssignedEngineer(request.getAssignedEngineer());
        }

        if (request.getEstimatedCompletion() != null) {
            jobCard.setEstimatedCompletion(request.getEstimatedCompletion());
        }
    }

}
