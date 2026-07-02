package com.microservice.kochimetro.train.service;

import com.microservice.kochimetro.exception.BadRequestException;
import com.microservice.kochimetro.exception.ResourceNotFoundException;
import com.microservice.kochimetro.train.dto.request.CreateTrainRequest;
import com.microservice.kochimetro.train.dto.request.UpdateTrainRequest;
import com.microservice.kochimetro.train.dto.response.TrainResponse;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.entity.enums.TrainStatus;
import com.microservice.kochimetro.train.mapper.TrainMapper;
import com.microservice.kochimetro.train.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    private Train findByIdHelper(UUID id) {
        Train train = trainRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train with the given id cannot be found: " + id));

        return train;
    }

    //create
    @Override
    public TrainResponse createTrain(CreateTrainRequest request) {
        if (trainRepository.existsByTrainNumber(request.getTrainNumber())) {
            throw new BadRequestException("Train number already exists");
        }

        Train train = TrainMapper.toEntity(request);

        train.setMileage(0);
        train.setStatus(TrainStatus.STANDBY);

        Train savedTrain = trainRepository.save(train);

        return TrainMapper.toResponse(savedTrain);
    }

    //get Train By Id
    @Override
    public TrainResponse getTrainById(UUID id) {
        Train train = findByIdHelper(id);
        return TrainMapper.toResponse(train);
    }


    //get Train by Train Id
    @Override
    public TrainResponse getTrainByTrainNumber(String trainNumber) {
        Train train = trainRepository
                .findByTrainNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train with the given train number cannot be found: " + trainNumber));
        return TrainMapper.toResponse(train);
    }

    //get All Trains
    @Override
    public List<TrainResponse> getAllTrains() {
        List<Train> trains = trainRepository.findAll();
        return trains.stream().map(TrainMapper::toResponse).toList();
    }

    //update the Train
    @Override
    public TrainResponse updateTrain(UUID id, UpdateTrainRequest request) {

        Train existingTrain = findByIdHelper(id);

        existingTrain.setTrainNumber(request.getTrainNumber());
        existingTrain.setModel(request.getModel());
        existingTrain.setDepot(request.getDepot());
        existingTrain.setBrandingStatus(request.getBrandingStatus());

        Train updatedTrain = trainRepository.save(existingTrain);

        return TrainMapper.toResponse(updatedTrain);
    }

    @Override
    public void deleteTrain(UUID id) {
        Train train = findByIdHelper(id);
        trainRepository.delete(train);
    }
}
