package com.microservice.kochimetro.ai.service;

import com.microservice.kochimetro.optimization.dto.response.SelectedTrainResponse;
import com.microservice.kochimetro.optimization.model.TrainData;

import java.util.List;
import java.util.Map;

public interface AIExplanationService {
    void enrichWithAIExplanations(
            List<TrainData> trains,
            List<SelectedTrainResponse> selectedTrains
    );
}
