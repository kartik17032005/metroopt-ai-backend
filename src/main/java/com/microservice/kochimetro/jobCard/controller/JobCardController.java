package com.microservice.kochimetro.jobCard.controller;

import com.microservice.kochimetro.common.response.ApiResponse;
import com.microservice.kochimetro.jobCard.dto.request.CreateJobCardRequest;
import com.microservice.kochimetro.jobCard.dto.request.UpdateJobCardRequest;
import com.microservice.kochimetro.jobCard.dto.response.JobCardResponse;
import com.microservice.kochimetro.jobCard.service.JobCardService;
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
@RequestMapping("/api/job-cards")
public class JobCardController {

    private final JobCardService jobCardService;

    public JobCardController(JobCardService jobCardService) {
        this.jobCardService = jobCardService;
    }

    private <T> ApiResponse<T> apiResponse(String message, T response) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(response)
                .timestamp(Instant.now())
                .build();
    }

    //get All job cards
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<JobCardResponse>>> getAllJobCards() {
        log.info("Fetching all the job cards...");

        List<JobCardResponse> jobCardResponses = jobCardService.getAllJobCards();

        log.info("Successfully fetched all the job cards");

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse("Successfully Fetched all the job cards", jobCardResponses));
    }

    //get job card with the id;
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<JobCardResponse>> getJobCardById(@PathVariable UUID id) {
        log.info("Fetching the job card with the id: " + id);
        JobCardResponse jobCardResponse = jobCardService.getJobCardById(id);
        log.info("Job card fetched successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse("Successfully fetched the job card with the id: " + id, jobCardResponse));
    }

    //create job card
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<ApiResponse<JobCardResponse>> createJobCard(@Valid @RequestBody CreateJobCardRequest jobCardRequest) {
        log.info("Creating job card...");
        JobCardResponse jobCardResponse = jobCardService.createJobCard(jobCardRequest);
        log.info("Successfully created the job card");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse("Successfully created the job card", jobCardResponse));
    }

    //update job card
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<ApiResponse<JobCardResponse>> updateJobCard(@PathVariable UUID id, @Valid @RequestBody UpdateJobCardRequest updateJobCardRequest) {
        log.info("Updating the job card with the id: " + id);
        JobCardResponse jobCardResponse = jobCardService.updateJobCard(id, updateJobCardRequest);
        log.info("Successfully updated the job card with the id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse("Successfully updated the job card", jobCardResponse));
    }

    //get job cards by train id
    @GetMapping("/train/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<ApiResponse<List<JobCardResponse>>> getJobCardByTrainId(@PathVariable UUID id) {
        log.info("Fetching the job card with the train id: " + id);
        List<JobCardResponse> jobCardResponse = jobCardService.getJobCardByTrainId(id);
        log.info("Successfully fetched job card with the train id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse("Successfully fetched the job card with the train id: " + id, jobCardResponse));
    }

    //delete job card
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<ApiResponse<Void>> deleteJobCardById(@PathVariable UUID id) {
        log.info("Deleting the job card with the id: " + id);
        jobCardService.deleteJobCard(id);
        log.info("Successfully deleted job card with the id: " + id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
