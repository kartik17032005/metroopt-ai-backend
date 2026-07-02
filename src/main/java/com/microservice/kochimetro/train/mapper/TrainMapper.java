package com.microservice.kochimetro.train.mapper;

import com.microservice.kochimetro.train.dto.request.CreateTrainRequest;
import com.microservice.kochimetro.train.dto.response.TrainResponse;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.entity.enums.TrainStatus;

//i receiver the request from the controller in the form of json and dto
//but dto is not responsible for preserving data in the database
//thats why conversion is need to convert from dto -> entity and entity -> dto
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

    public static TrainResponse toDTO(Train train){
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
