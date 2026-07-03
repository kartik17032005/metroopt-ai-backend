package com.microservice.kochimetro.mileage.service;

import com.microservice.kochimetro.mileage.dto.request.CreateMileageRecordRequest;
import com.microservice.kochimetro.mileage.dto.request.UpdateMileageRecordRequest;
import com.microservice.kochimetro.mileage.dto.response.MileageRecordResponse;

import java.util.List;
import java.util.UUID;

public interface MileageRecordService {
    MileageRecordResponse createMileageRecord(CreateMileageRecordRequest request);
    MileageRecordResponse getMileageRecordById(UUID id);
    List<MileageRecordResponse> getMileageRecordsByTrainId(UUID trainId);
    List<MileageRecordResponse> getAllMileageRecords();
    MileageRecordResponse updateMileageRecord(UUID id, UpdateMileageRecordRequest request);
    void deleteMileageRecord(UUID id);
}
