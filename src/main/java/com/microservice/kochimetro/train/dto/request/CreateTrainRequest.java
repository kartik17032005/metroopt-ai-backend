package com.microservice.kochimetro.train.dto.request;

import com.microservice.kochimetro.train.entity.enums.BrandingStatus;
import com.microservice.kochimetro.train.entity.enums.Depot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Request payload for creating a new train.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTrainRequest {

    @NotBlank(message = "Train number is required")
    @Size(min = 3, max = 20, message = "Train number must be between 3 and 20 characters.")
    private String trainNumber;

    @NotBlank(message = "Model is required")
    @Size(min = 2, max = 50, message = "Model must be between 2 and 50 characters")
    private String model;

    @NotNull(message = "Depot is required")
    private Depot depot;

    @NotNull(message = "Branding status is required")
    private BrandingStatus brandingStatus;
}
