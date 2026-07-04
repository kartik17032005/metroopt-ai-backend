package com.microservice.kochimetro.fitness.controller;

import com.microservice.kochimetro.common.response.ApiResponse;
import com.microservice.kochimetro.fitness.dto.request.CreateFitnessCertificateRequest;
import com.microservice.kochimetro.fitness.dto.request.UpdateFitnessCertificateRequest;
import com.microservice.kochimetro.fitness.dto.response.FitnessCertificateResponse;
import com.microservice.kochimetro.fitness.service.FitnessCertificateService;
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
@RequestMapping("/api/fitness-certificates")
public class FitnessCertificateController {

    private final FitnessCertificateService fitnessCertificateService;

    public FitnessCertificateController(FitnessCertificateService fitnessCertificateService) {
        this.fitnessCertificateService = fitnessCertificateService;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<ApiResponse<FitnessCertificateResponse>> createFitnessCertificate(
            @Valid @RequestBody CreateFitnessCertificateRequest request) {
        log.info("Creating fitness certificate with number: {}", request.getCertificateNumber());
        FitnessCertificateResponse response = fitnessCertificateService.createFitnessCertificate(request);
        log.info("Successfully created fitness certificate with ID: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse("Fitness Certificate Created Successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<FitnessCertificateResponse>> getFitnessCertificateById(@PathVariable UUID id) {
        log.info("Fetching fitness certificate with ID: {}", id);
        FitnessCertificateResponse response = fitnessCertificateService.getFitnessCertificateById(id);
        log.info("Successfully fetched fitness certificate with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Fitness certificate fetched successfully with the id: " + id, response));
    }

    @GetMapping("/certificate-number/{certificateNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<FitnessCertificateResponse>> getFitnessCertificateByCertificateNumber(
            @PathVariable String certificateNumber) {
        log.info("Fetching fitness certificate with number: {}", certificateNumber);
        FitnessCertificateResponse response = fitnessCertificateService.getFitnessCertificateByCertificateNumber(certificateNumber);
        log.info("Successfully fetched fitness certificate with number: {}", certificateNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Fitness certificate fetched successfully with the certificateNumber: " + certificateNumber, response));
    }

    @GetMapping("/train/{trainId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<FitnessCertificateResponse>>> getFitnessCertificatesByTrainId(
            @PathVariable UUID trainId) {
        log.info("Fetching fitness certificates for train ID: {}", trainId);
        List<FitnessCertificateResponse> response = fitnessCertificateService.getFitnessCertificatesByTrainId(trainId);
        log.info("Successfully fetched fitness certificates for train ID: {}", trainId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Fitness certificates fetched successfully for the trainId: " + trainId, response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<FitnessCertificateResponse>>> getAllFitnessCertificates() {
        log.info("Fetching all fitness certificates");
        List<FitnessCertificateResponse> response = fitnessCertificateService.getAllFitnessCertificates();
        log.info("Successfully fetched all fitness certificates");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("All fitness certificates fetched successfully", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<ApiResponse<FitnessCertificateResponse>> updateFitnessCertificate(
            @PathVariable UUID id, @Valid @RequestBody UpdateFitnessCertificateRequest request) {
        log.info("Updating fitness certificate with ID: {}", id);
        FitnessCertificateResponse response = fitnessCertificateService.updateFitnessCertificate(id, request);
        log.info("Successfully updated fitness certificate with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Fitness certificate is updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Void> deleteFitnessCertificate(@PathVariable UUID id) {
        log.info("Deleting fitness certificate with ID: {}", id);
        fitnessCertificateService.deleteFitnessCertificate(id);
        log.info("Successfully deleted fitness certificate with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
