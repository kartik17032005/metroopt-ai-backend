package com.microservice.kochimetro.mileage.mapper;

import com.microservice.kochimetro.mileage.dto.request.CreateMileageRecordRequest;
import com.microservice.kochimetro.mileage.dto.request.UpdateMileageRecordRequest;
import com.microservice.kochimetro.mileage.dto.response.MileageRecordResponse;
import com.microservice.kochimetro.mileage.entity.MileageRecord;
import com.microservice.kochimetro.train.entity.Train;

public final class MileageRecordMapper {

    private MileageRecordMapper() {
    }

    public static MileageRecord toEntity(CreateMileageRecordRequest request, Train train) {
        return MileageRecord.builder()
                .train(train)
                .mileage(request.getMileage())
                .recordedAt(request.getRecordedAt())
                .recordedBy(request.getRecordedBy())
                .build();
    }

    public static MileageRecordResponse toResponse(MileageRecord record) {
        return MileageRecordResponse.builder()
                .id(record.getId())
                .trainId(record.getTrain().getId())
                .trainNumber(record.getTrain().getTrainNumber())
                .mileage(record.getMileage())
                .recordedAt(record.getRecordedAt())
                .recordedBy(record.getRecordedBy())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .build();
    }

    public static void updateEntity(MileageRecord record, UpdateMileageRecordRequest request) {
        if (request.getMileage() != null) {
            record.setMileage(request.getMileage());
        }
        if (request.getRecordedAt() != null) {
            record.setRecordedAt(request.getRecordedAt());
        }
        if (request.getRecordedBy() != null) {
            record.setRecordedBy(request.getRecordedBy());
        }
    }
}
