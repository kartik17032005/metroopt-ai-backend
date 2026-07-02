package com.microservice.kochimetro.train.mapper;

import com.microservice.kochimetro.train.dto.request.CreateTrainRequest;
import com.microservice.kochimetro.train.dto.response.TrainResponse;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.entity.enums.TrainStatus;

/**
 * Utility class responsible for converting between
 * Train entities and Train DTOs.
 */

public final class TrainMapper {

    private TrainMapper(){}

    public static Train toEntity(CreateTrainRequest request){
        return Train.builder()
                .trainNumber(request.getTrainNumber())
                .model(request.getModel())
                .brandingStatus(request.getBrandingStatus())
                .mileage(0)
                .depot(request.getDepot())
                .status(TrainStatus.STANDBY)
                .build();
    }

    public static TrainResponse toResponse(Train train){
        return TrainResponse.builder()
                .id(train.getId())
                .trainNumber(train.getTrainNumber())
                .model(train.getModel())
                .mileage(train.getMileage())
                .status(train.getStatus())
                .depot(train.getDepot())
                .brandingStatus(train.getBrandingStatus())
                .createdAt(train.getCreatedAt())
                .updatedAt(train.getUpdatedAt())
                .build();
    }
}
