package com.microservice.kochimetro.mileage.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MileagePredictionResponse {
    private String trainNumber;
    private Integer currentMileage;
    private Double dailyMileageRate; //slope
    private Integer predictedMileageIn7Days;
    private LocalDate predictionDate;
    private Double modelAccuracy;
    private String riskLevel;
    private String recommendation;
}
