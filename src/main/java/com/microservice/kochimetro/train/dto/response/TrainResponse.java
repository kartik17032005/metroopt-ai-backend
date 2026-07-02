package com.microservice.kochimetro.train.dto.response;

import com.microservice.kochimetro.train.entity.enums.BrandingStatus;
import com.microservice.kochimetro.train.entity.enums.Depot;
import com.microservice.kochimetro.train.entity.enums.TrainStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainResponse {
    private UUID id;
    private String trainNumber;
    private String model;
    private Integer mileage;
    private TrainStatus status;
    private Depot depot;
    private BrandingStatus brandingStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
