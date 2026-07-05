package com.microservice.kochimetro.optimization.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptimizationResponse {

    //now the optimization algorithm will give number of responses
    private List<SelectedTrainResponse> selectedTrains;

    private int totalSelected;

    private String solverStatus;

}