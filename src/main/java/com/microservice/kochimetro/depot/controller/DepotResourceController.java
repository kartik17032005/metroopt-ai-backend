package com.microservice.kochimetro.depot.controller;

import com.microservice.kochimetro.common.response.ApiResponse;
import com.microservice.kochimetro.depot.dto.request.CreateDepotResourceRequest;
import com.microservice.kochimetro.depot.dto.request.UpdateDepotResourceRequest;
import com.microservice.kochimetro.depot.dto.response.DepotResourceResponse;
import com.microservice.kochimetro.depot.service.DepotResourceService;
import com.microservice.kochimetro.train.entity.enums.Depot;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/depot-resources")
@RequiredArgsConstructor
public class DepotResourceController {

    private final DepotResourceService depotResourceService;

    private <T> ApiResponse<T> apiResponse(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepotResourceResponse>> create(@Valid @RequestBody CreateDepotResourceRequest request) {
        log.info("Creating depot resource for depot: {}", request.getDepot());
        DepotResourceResponse response = depotResourceService.create(request);
        log.info("Successfully created depot resource with ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse("Depot Resource Created Successfully", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepotResourceResponse>> update(@PathVariable UUID id,
                                                                     @Valid @RequestBody UpdateDepotResourceRequest request) {
        log.info("Updating depot resource with ID: {}", id);
        DepotResourceResponse response = depotResourceService.update(id, request);
        log.info("Successfully updated depot resource with ID: {}", id);
        return ResponseEntity.ok(apiResponse("Depot Resource Updated Successfully", response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<DepotResourceResponse>>> getAll() {
        log.info("Fetching all depot resources");
        List<DepotResourceResponse> list = depotResourceService.getAll();
        log.info("Fetched {} depot resources", list.size());
        return ResponseEntity.ok(apiResponse("All Depot Resources Fetched Successfully", list));
    }

    @GetMapping("/depot/{depot}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<DepotResourceResponse>> getByDepot(@PathVariable Depot depot) {
        log.info("Fetching depot resource for depot: {}", depot);
        DepotResourceResponse response = depotResourceService.getByDepot(depot);
        return ResponseEntity.ok(apiResponse("Depot Resource Fetched Successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        log.info("Deleting depot resource with ID: {}", id);
        depotResourceService.delete(id);
        log.info("Deleted depot resource with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
