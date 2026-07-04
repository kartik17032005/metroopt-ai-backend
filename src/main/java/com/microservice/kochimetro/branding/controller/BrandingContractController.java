package com.microservice.kochimetro.branding.controller;

import com.microservice.kochimetro.branding.dto.request.CreateBrandingContractRequest;
import com.microservice.kochimetro.branding.dto.request.UpdateBrandingContractRequest;
import com.microservice.kochimetro.branding.dto.response.BrandingContractResponse;
import com.microservice.kochimetro.branding.service.BrandingContractService;
import com.microservice.kochimetro.common.response.ApiResponse;
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
@RequestMapping("/api/branding-contracts")
public class BrandingContractController {

    private final BrandingContractService brandingContractService;

    public BrandingContractController(BrandingContractService brandingContractService) {
        this.brandingContractService = brandingContractService;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BrandingContractResponse>> createBrandingContract(
            @Valid @RequestBody CreateBrandingContractRequest request) {
        log.info("Creating branding contract for advertiser: {}", request.getAdvertiser());
        BrandingContractResponse response = brandingContractService.createBrandingContract(request);
        log.info("Successfully created branding contract with ID: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse("Branding Contract Created Successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<BrandingContractResponse>> getBrandingContractById(@PathVariable UUID id) {
        log.info("Fetching branding contract with ID: {}", id);
        BrandingContractResponse response = brandingContractService.getBrandingContractById(id);
        log.info("Successfully fetched branding contract with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Branding contract fetched successfully with the id: " + id, response));
    }

    @GetMapping("/train/{trainId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<BrandingContractResponse>>> getBrandingContractsByTrainId(
            @PathVariable UUID trainId) {
        log.info("Fetching branding contracts for train ID: {}", trainId);
        List<BrandingContractResponse> response = brandingContractService.getBrandingContractsByTrainId(trainId);
        log.info("Successfully fetched branding contracts for train ID: {}", trainId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Branding contracts fetched successfully for the trainId: " + trainId, response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<BrandingContractResponse>>> getAllBrandingContracts() {
        log.info("Fetching all branding contracts");
        List<BrandingContractResponse> response = brandingContractService.getAllBrandingContracts();
        log.info("Successfully fetched all branding contracts");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("All branding contracts fetched successfully", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BrandingContractResponse>> updateBrandingContract(
            @PathVariable UUID id, @Valid @RequestBody UpdateBrandingContractRequest request) {
        log.info("Updating branding contract with ID: {}", id);
        BrandingContractResponse response = brandingContractService.updateBrandingContract(id, request);
        log.info("Successfully updated branding contract with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Branding contract is updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBrandingContract(@PathVariable UUID id) {
        log.info("Deleting branding contract with ID: {}", id);
        brandingContractService.deleteBrandingContract(id);
        log.info("Successfully deleted branding contract with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
