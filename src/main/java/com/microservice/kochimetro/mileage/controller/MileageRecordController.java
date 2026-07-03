package com.microservice.kochimetro.mileage.controller;

import com.microservice.kochimetro.common.response.ApiResponse;
import com.microservice.kochimetro.mileage.dto.request.CreateMileageRecordRequest;
import com.microservice.kochimetro.mileage.dto.request.UpdateMileageRecordRequest;
import com.microservice.kochimetro.mileage.dto.response.MileageRecordResponse;
import com.microservice.kochimetro.mileage.service.MileageRecordService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/mileage-records")
public class MileageRecordController {

    private final MileageRecordService mileageRecordService;

    public MileageRecordController(MileageRecordService mileageRecordService) {
        this.mileageRecordService = mileageRecordService;
    }

    private <T> ApiResponse<T> apiResponse(String message, T response) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(response)
                .timestamp(Instant.now())
                .build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MileageRecordResponse>> createMileageRecord(
            @Valid @RequestBody CreateMileageRecordRequest request) {
        log.info("Creating mileage record for train ID: {}", request.getTrainId());
        MileageRecordResponse response = mileageRecordService.createMileageRecord(request);
        log.info("Successfully created mileage record with ID: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse("Mileage Record Created Successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MileageRecordResponse>> getMileageRecordById(@PathVariable UUID id) {
        log.info("Fetching mileage record with ID: {}", id);
        MileageRecordResponse response = mileageRecordService.getMileageRecordById(id);
        log.info("Successfully fetched mileage record with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Mileage record fetched successfully with the id: " + id, response));
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<ApiResponse<List<MileageRecordResponse>>> getMileageRecordsByTrainId(
            @PathVariable UUID trainId) {
        log.info("Fetching mileage records for train ID: {}", trainId);
        List<MileageRecordResponse> response = mileageRecordService.getMileageRecordsByTrainId(trainId);
        log.info("Successfully fetched mileage records for train ID: {}", trainId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Mileage records fetched successfully for the trainId: " + trainId, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MileageRecordResponse>>> getAllMileageRecords() {
        log.info("Fetching all mileage records");
        List<MileageRecordResponse> response = mileageRecordService.getAllMileageRecords();
        log.info("Successfully fetched all mileage records");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("All mileage records fetched successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MileageRecordResponse>> updateMileageRecord(
            @PathVariable UUID id, @Valid @RequestBody UpdateMileageRecordRequest request) {
        log.info("Updating mileage record with ID: {}", id);
        MileageRecordResponse response = mileageRecordService.updateMileageRecord(id, request);
        log.info("Successfully updated mileage record with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Mileage record is updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMileageRecord(@PathVariable UUID id) {
        log.info("Deleting mileage record with ID: {}", id);
        mileageRecordService.deleteMileageRecord(id);
        log.info("Successfully deleted mileage record with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
