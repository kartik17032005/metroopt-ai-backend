package com.microservice.kochimetro.train.service;

import com.microservice.kochimetro.train.dto.request.CreateTrainRequest;
import com.microservice.kochimetro.train.dto.request.UpdateTrainRequest;
import com.microservice.kochimetro.train.dto.response.TrainResponse;

import java.util.List;
import java.util.UUID;

public interface TrainService {
    TrainResponse createTrain(CreateTrainRequest request);

    TrainResponse getTrainById(UUID id);

    TrainResponse getTrainByTrainNumber(String trainNumber);

    List<TrainResponse> getAllTrains();

    TrainResponse updateTrain(UUID id, UpdateTrainRequest request);

    void deleteTrain(UUID id);
}
