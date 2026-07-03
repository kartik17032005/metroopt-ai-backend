package com.microservice.kochimetro.cleaning.mapper;

import com.microservice.kochimetro.cleaning.dto.request.CreateCleaningSlotRequest;
import com.microservice.kochimetro.cleaning.dto.request.UpdateCleaningSlotRequest;
import com.microservice.kochimetro.cleaning.dto.response.CleaningSlotResponse;
import com.microservice.kochimetro.cleaning.entity.CleaningSlot;
import com.microservice.kochimetro.cleaning.entity.enums.CleaningStatus;
import com.microservice.kochimetro.train.entity.Train;

public final class CleaningSlotMapper {

    private CleaningSlotMapper() {
    }

    public static CleaningSlot toEntity(CreateCleaningSlotRequest request, Train train) {
        return CleaningSlot.builder()
                .train(train)
                .cleaningType(request.getCleaningType())
                .status(CleaningStatus.SCHEDULED)
                .scheduledTime(request.getScheduledTime())
                .assignedStaff(request.getAssignedStaff())
                .build();
    }

    public static CleaningSlotResponse toResponse(CleaningSlot slot) {
        return CleaningSlotResponse.builder()
                .id(slot.getId())
                .trainId(slot.getTrain().getId())
                .trainNumber(slot.getTrain().getTrainNumber())
                .cleaningType(slot.getCleaningType())
                .status(slot.getStatus())
                .scheduledTime(slot.getScheduledTime())
                .completedTime(slot.getCompletedTime())
                .assignedStaff(slot.getAssignedStaff())
                .createdAt(slot.getCreatedAt())
                .updatedAt(slot.getUpdatedAt())
                .build();
    }

    public static void updateEntity(CleaningSlot slot, UpdateCleaningSlotRequest request) {
        if (request.getCleaningType() != null) {
            slot.setCleaningType(request.getCleaningType());
        }
        if (request.getStatus() != null) {
            slot.setStatus(request.getStatus());
        }
        if (request.getScheduledTime() != null) {
            slot.setScheduledTime(request.getScheduledTime());
        }
        if (request.getCompletedTime() != null) {
            slot.setCompletedTime(request.getCompletedTime());
        }
        if (request.getAssignedStaff() != null) {
            slot.setAssignedStaff(request.getAssignedStaff());
        }
    }
}
