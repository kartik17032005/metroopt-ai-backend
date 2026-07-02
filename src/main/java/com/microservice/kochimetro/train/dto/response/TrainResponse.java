package com.microservice.kochimetro.train.dto.response;

import com.microservice.kochimetro.train.entity.enums.BrandingStatus;
import com.microservice.kochimetro.train.entity.enums.Depot;
import com.microservice.kochimetro.train.entity.enums.TrainStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
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
