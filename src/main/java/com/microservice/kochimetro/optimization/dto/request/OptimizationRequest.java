package com.microservice.kochimetro.optimization.dto.request;

import lombok.*;

//how many trains like 18, 20 etc
/*
{
  "requiredOperatingTrains": 20,
  "requiredStandbyTrains": 3,
  "requiredInspectionTrains": 2
}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptimizationRequest {
    private int requiredOperatingTrains;
    private Integer requiredStandByTrains;
    private Integer requiredInspectionTrains;
}
