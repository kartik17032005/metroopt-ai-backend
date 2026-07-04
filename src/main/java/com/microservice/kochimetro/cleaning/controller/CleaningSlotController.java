package com.microservice.kochimetro.cleaning.controller;

import com.microservice.kochimetro.cleaning.dto.request.CreateCleaningSlotRequest;
import com.microservice.kochimetro.cleaning.dto.request.UpdateCleaningSlotRequest;
import com.microservice.kochimetro.cleaning.dto.response.CleaningSlotResponse;
import com.microservice.kochimetro.cleaning.service.CleaningSlotService;
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
@RequestMapping("/api/cleaning-slots")
public class CleaningSlotController {

    private final CleaningSlotService cleaningSlotService;

    public CleaningSlotController(CleaningSlotService cleaningSlotService) {
        this.cleaningSlotService = cleaningSlotService;
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
    public ResponseEntity<ApiResponse<CleaningSlotResponse>> createCleaningSlot(
            @Valid @RequestBody CreateCleaningSlotRequest request) {
        log.info("Creating cleaning slot for train ID: {}", request.getTrainId());
        CleaningSlotResponse response = cleaningSlotService.createCleaningSlot(request);
        log.info("Successfully created cleaning slot with ID: {}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse("Cleaning Slot Created Successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<CleaningSlotResponse>> getCleaningSlotById(@PathVariable UUID id) {
        log.info("Fetching cleaning slot with ID: {}", id);
        CleaningSlotResponse response = cleaningSlotService.getCleaningSlotById(id);
        log.info("Successfully fetched cleaning slot with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Cleaning slot fetched successfully with the id: " + id, response));
    }

    @GetMapping("/train/{trainId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<CleaningSlotResponse>>> getCleaningSlotsByTrainId(
            @PathVariable UUID trainId) {
        log.info("Fetching cleaning slots for train ID: {}", trainId);
        List<CleaningSlotResponse> response = cleaningSlotService.getCleaningSlotsByTrainId(trainId);
        log.info("Successfully fetched cleaning slots for train ID: {}", trainId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Cleaning slots fetched successfully for the trainId: " + trainId, response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<CleaningSlotResponse>>> getAllCleaningSlots() {
        log.info("Fetching all cleaning slots");
        List<CleaningSlotResponse> response = cleaningSlotService.getAllCleaningSlots();
        log.info("Successfully fetched all cleaning slots");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("All cleaning slots fetched successfully", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<ApiResponse<CleaningSlotResponse>> updateCleaningSlot(
            @PathVariable UUID id, @Valid @RequestBody UpdateCleaningSlotRequest request) {
        log.info("Updating cleaning slot with ID: {}", id);
        CleaningSlotResponse response = cleaningSlotService.updateCleaningSlot(id, request);
        log.info("Successfully updated cleaning slot with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse("Cleaning slot is updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Void> deleteCleaningSlot(@PathVariable UUID id) {
        log.info("Deleting cleaning slot with ID: {}", id);
        cleaningSlotService.deleteCleaningSlot(id);
        log.info("Successfully deleted cleaning slot with ID: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
