package com.microservice.kochimetro.train.controller;

import com.microservice.kochimetro.common.response.ApiResponse;
import com.microservice.kochimetro.train.dto.request.CreateTrainRequest;
import com.microservice.kochimetro.train.dto.request.UpdateTrainRequest;
import com.microservice.kochimetro.train.dto.response.TrainResponse;
import com.microservice.kochimetro.train.service.TrainService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/trains")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    //common apiResponse method
    private <T> ApiResponse<T> apiResponse(String message, T response) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(response)
                .timestamp(Instant.now())
                .build();
    }

    //create
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TrainResponse>> createTrain(@Valid @RequestBody CreateTrainRequest request) {
        log.info("Creating train with train number: {}", request.getTrainNumber());
        TrainResponse response = trainService.createTrain(request);
        log.info(
                "Successfully created train with ID: {}",
                response.getId()
        );

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(apiResponse("Train Created Successfully", response));
    }

    //get all trains
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER' ,'OPERATOR')")
    public ResponseEntity<ApiResponse<List<TrainResponse>>> getAllTrains() {
        log.info("Fetching all the trains");
        List<TrainResponse> trains = trainService.getAllTrains();
        log.info("Successfully fetched all the trains");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("All trains fetched successfully", trains));
    }

    //get Train By id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER' ,'OPERATOR')")
    public ResponseEntity<ApiResponse<TrainResponse>> getTrainById(@PathVariable UUID id) {
        log.info("Fetching the train with the id: {}", id);
        TrainResponse train = trainService.getTrainById(id);
        log.info(
                "Successfully fetched train with ID: {}",
                id
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Train fetched successfully with the id: " + id, train));
    }

    //get train by train number
    @GetMapping("/train-number/{trainNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER' ,'OPERATOR')")
    public ResponseEntity<ApiResponse<TrainResponse>> getTrainByTrainNumber(@PathVariable String trainNumber) {
        log.info("Fetching the train with the trainNumber: {}", trainNumber);
        TrainResponse train = trainService.getTrainByTrainNumber(trainNumber);
        log.info(
                "Successfully fetched train with trainNumber: {}",
                trainNumber
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Train fetched successfully with the trainNumber: " + trainNumber, train));
    }

    //update the train
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TrainResponse>> updateTrain(@PathVariable UUID id, @Valid @RequestBody UpdateTrainRequest request) {
        log.info("Updating the train with the id:{} and the data for the particular train is: {}", id, request.getTrainNumber());
        TrainResponse response = trainService.updateTrain(id, request);

        log.info("Successfully updated the train with the id:{} and the data for the particular train is: {}", id, request.getTrainNumber());

        return ResponseEntity.
                status(HttpStatus.OK)
                .body(apiResponse("Train is updated successfully", response));
    }

    //delete the train
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTrain(@PathVariable UUID id) {
        log.info("Deleting the train with id: {}", id);
        trainService.deleteTrain(id);
        log.info("Successfully deleted the train with id: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
