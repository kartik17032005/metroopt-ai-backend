package com.microservice.kochimetro.optimization.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("requiredStandbyTrains")
    @JsonAlias({"requiredStandByTrains", "required_standby_trains"})
    private Integer requiredStandByTrains;

    @JsonProperty("requiredInspectionTrains")
    @JsonAlias({"required_inspection_trains"})
    private Integer requiredInspectionTrains;

    @JsonProperty("algorithm")
    @JsonAlias({"strategy", "optimization_algorithm", "optimizationAlgorithm"})
    private String algorithm;

    public int getRequiredStandByTrainsCount() {
        return requiredStandByTrains != null ? requiredStandByTrains : 0;
    }

    public int getRequiredInspectionTrainsCount() {
        return requiredInspectionTrains != null ? requiredInspectionTrains : 0;
    }
}
