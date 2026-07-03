package com.microservice.kochimetro.mileage.service;

import com.microservice.kochimetro.exception.ResourceNotFoundException;
import com.microservice.kochimetro.mileage.dto.request.CreateMileageRecordRequest;
import com.microservice.kochimetro.mileage.dto.request.UpdateMileageRecordRequest;
import com.microservice.kochimetro.mileage.dto.response.MileageRecordResponse;
import com.microservice.kochimetro.mileage.entity.MileageRecord;
import com.microservice.kochimetro.mileage.mapper.MileageRecordMapper;
import com.microservice.kochimetro.mileage.repository.MileageRecordRepository;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MileageRecordServiceImpl implements MileageRecordService {

    private final MileageRecordRepository mileageRecordRepository;
    private final TrainRepository trainRepository;

    public MileageRecordServiceImpl(MileageRecordRepository mileageRecordRepository, TrainRepository trainRepository) {
        this.mileageRecordRepository = mileageRecordRepository;
        this.trainRepository = trainRepository;
    }

    private MileageRecord findByIdHelper(UUID id) {
        return mileageRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mileage record with the given id cannot be found: " + id));
    }

    @Override
    public MileageRecordResponse createMileageRecord(CreateMileageRecordRequest request) {
        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + request.getTrainId()));

        MileageRecord record = MileageRecordMapper.toEntity(request, train);
        
        // Update train mileage to the newly recorded mileage
        train.setMileage(request.getMileage());
        trainRepository.save(train);

        MileageRecord savedRecord = mileageRecordRepository.save(record);

        return MileageRecordMapper.toResponse(savedRecord);
    }

    @Override
    public MileageRecordResponse getMileageRecordById(UUID id) {
        MileageRecord record = findByIdHelper(id);
        return MileageRecordMapper.toResponse(record);
    }

    @Override
    public List<MileageRecordResponse> getMileageRecordsByTrainId(UUID trainId) {
        trainRepository.findById(trainId)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainId));

        List<MileageRecord> records = mileageRecordRepository.findByTrainId(trainId);
        return records.stream().map(MileageRecordMapper::toResponse).toList();
    }

    @Override
    public List<MileageRecordResponse> getAllMileageRecords() {
        List<MileageRecord> records = mileageRecordRepository.findAll();
        return records.stream().map(MileageRecordMapper::toResponse).toList();
    }

    @Override
    public MileageRecordResponse updateMileageRecord(UUID id, UpdateMileageRecordRequest request) {
        MileageRecord existingRecord = findByIdHelper(id);

        MileageRecordMapper.updateEntity(existingRecord, request);
        
        // Keep train mileage in sync if updated
        Train train = existingRecord.getTrain();
        train.setMileage(request.getMileage());
        trainRepository.save(train);

        MileageRecord updatedRecord = mileageRecordRepository.save(existingRecord);

        return MileageRecordMapper.toResponse(updatedRecord);
    }

    @Override
    public void deleteMileageRecord(UUID id) {
        MileageRecord record = findByIdHelper(id);
        mileageRecordRepository.delete(record);
    }
}
