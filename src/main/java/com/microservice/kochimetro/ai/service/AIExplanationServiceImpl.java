package com.microservice.kochimetro.ai.service;

import com.microservice.kochimetro.optimization.orTools.AllocationStatus;
import com.microservice.kochimetro.train.entity.Train;
import org.springframework.stereotype.Service;

import java.io.Serial;

@Service
public class AIExplanationServiceImpl implements AIExplanationService {
    @Override
    public String generateExplanation(Train train, AllocationStatus allocationStatus) {
        return "Ai explanation";
    }
}
