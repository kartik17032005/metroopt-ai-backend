package com.microservice.kochimetro.ai.service;

import com.microservice.kochimetro.optimization.orTools.AllocationStatus;
import com.microservice.kochimetro.train.entity.Train;

public interface AIExplanationService {
    String generateExplanation(
            Train train,
            AllocationStatus allocationStatus
    );
}
