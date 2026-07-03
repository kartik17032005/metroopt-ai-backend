package com.microservice.kochimetro.jobCard.service;

import com.microservice.kochimetro.jobCard.dto.request.CreateJobCardRequest;
import com.microservice.kochimetro.jobCard.dto.request.UpdateJobCardRequest;
import com.microservice.kochimetro.jobCard.dto.response.JobCardResponse;
import com.microservice.kochimetro.train.entity.Train;

import java.util.List;
import java.util.UUID;

public interface JobCardService {
    JobCardResponse createJobCard(CreateJobCardRequest jobCardRequest);

    JobCardResponse getJobCardById(UUID id);

    List<JobCardResponse> getJobCardByTrainId(UUID id);

    List<JobCardResponse> getAllJobCards();

    JobCardResponse updateJobCard(UUID id, UpdateJobCardRequest request);

    void deleteJobCard(UUID id);

}
