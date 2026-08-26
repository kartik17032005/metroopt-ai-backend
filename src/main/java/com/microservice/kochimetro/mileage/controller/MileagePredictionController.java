package com.microservice.kochimetro.mileage.controller;

import com.microservice.kochimetro.mileage.dto.response.MileagePredictionResponse;
import com.microservice.kochimetro.mileage.service.MileagePredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/mileage")
public class MileagePredictionController {
    private final MileagePredictionService mileagePredictionService;

    public MileagePredictionController(MileagePredictionService mileagePredictionService) {
        this.mileagePredictionService = mileagePredictionService;
    }

    @GetMapping("/prediction/{trainId}")
    public ResponseEntity<MileagePredictionResponse> predictMileage(@PathVariable UUID trainId) {
        MileagePredictionResponse response = mileagePredictionService.predictMileage(trainId);

        return ResponseEntity.ok(response);
    }
}
