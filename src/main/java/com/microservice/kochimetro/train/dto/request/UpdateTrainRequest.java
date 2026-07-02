package com.microservice.kochimetro.train.dto.request;

import com.microservice.kochimetro.train.entity.enums.BrandingStatus;
import com.microservice.kochimetro.train.entity.enums.TrainStatus;

public class UpdateTrainRequest {
    private String depot;
    private BrandingStatus brandingStatus;
    private TrainStatus status;
}
