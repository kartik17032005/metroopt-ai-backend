package com.microservice.kochimetro.cleaning.service;

import com.microservice.kochimetro.cleaning.dto.request.CreateCleaningSlotRequest;
import com.microservice.kochimetro.cleaning.dto.request.UpdateCleaningSlotRequest;
import com.microservice.kochimetro.cleaning.dto.response.CleaningSlotResponse;
import com.microservice.kochimetro.cleaning.entity.CleaningSlot;
import com.microservice.kochimetro.cleaning.mapper.CleaningSlotMapper;
import com.microservice.kochimetro.cleaning.repository.CleaningSlotRepository;
import com.microservice.kochimetro.exception.ResourceNotFoundException;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CleaningSlotServiceImpl implements CleaningSlotService {

    private final CleaningSlotRepository cleaningSlotRepository;
    private final TrainRepository trainRepository;

    public CleaningSlotServiceImpl(CleaningSlotRepository cleaningSlotRepository, TrainRepository trainRepository) {
        this.cleaningSlotRepository = cleaningSlotRepository;
        this.trainRepository = trainRepository;
    }

    private CleaningSlot findByIdHelper(UUID id) {
        return cleaningSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cleaning slot with the given id cannot be found: " + id));
    }

    @Override
    public CleaningSlotResponse createCleaningSlot(CreateCleaningSlotRequest request) {
        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + request.getTrainId()));

        CleaningSlot slot = CleaningSlotMapper.toEntity(request, train);
        CleaningSlot savedSlot = cleaningSlotRepository.save(slot);

        return CleaningSlotMapper.toResponse(savedSlot);
    }

    @Override
    public CleaningSlotResponse getCleaningSlotById(UUID id) {
        CleaningSlot slot = findByIdHelper(id);
        return CleaningSlotMapper.toResponse(slot);
    }

    @Override
    public List<CleaningSlotResponse> getCleaningSlotsByTrainId(UUID trainId) {
        trainRepository.findById(trainId)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainId));

        List<CleaningSlot> slots = cleaningSlotRepository.findByTrainId(trainId);
        return slots.stream().map(CleaningSlotMapper::toResponse).toList();
    }

    @Override
    public List<CleaningSlotResponse> getAllCleaningSlots() {
        List<CleaningSlot> slots = cleaningSlotRepository.findAll();
        return slots.stream().map(CleaningSlotMapper::toResponse).toList();
    }

    @Override
    public CleaningSlotResponse updateCleaningSlot(UUID id, UpdateCleaningSlotRequest request) {
        CleaningSlot existingSlot = findByIdHelper(id);

        CleaningSlotMapper.updateEntity(existingSlot, request);
        CleaningSlot updatedSlot = cleaningSlotRepository.save(existingSlot);

        return CleaningSlotMapper.toResponse(updatedSlot);
    }

    @Override
    public void deleteCleaningSlot(UUID id) {
        CleaningSlot slot = findByIdHelper(id);
        cleaningSlotRepository.delete(slot);
    }
}
