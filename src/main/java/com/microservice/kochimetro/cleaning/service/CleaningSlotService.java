package com.microservice.kochimetro.cleaning.service;

import com.microservice.kochimetro.cleaning.dto.request.CreateCleaningSlotRequest;
import com.microservice.kochimetro.cleaning.dto.request.UpdateCleaningSlotRequest;
import com.microservice.kochimetro.cleaning.dto.response.CleaningSlotResponse;

import java.util.List;
import java.util.UUID;

public interface CleaningSlotService {
    CleaningSlotResponse createCleaningSlot(CreateCleaningSlotRequest request);
    CleaningSlotResponse getCleaningSlotById(UUID id);
    List<CleaningSlotResponse> getCleaningSlotsByTrainId(UUID trainId);
    List<CleaningSlotResponse> getAllCleaningSlots();
    CleaningSlotResponse updateCleaningSlot(UUID id, UpdateCleaningSlotRequest request);
    void deleteCleaningSlot(UUID id);
}
