package com.microservice.kochimetro.train.dto.request;

import com.microservice.kochimetro.train.entity.enums.BrandingStatus;
import com.microservice.kochimetro.train.entity.enums.Depot;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainRequest {
    private String trainNumber;
    private String model;

    @NotNull(message = "Depot is required")
    private Depot depot;

    @NotNull(message = "Branding status is required")
    private BrandingStatus brandingStatus;
}
