package com.microservice.kochimetro.jobCard.service;

import com.microservice.kochimetro.exception.ResourceNotFoundException;
import com.microservice.kochimetro.jobCard.dto.request.CreateJobCardRequest;
import com.microservice.kochimetro.jobCard.dto.request.UpdateJobCardRequest;
import com.microservice.kochimetro.jobCard.dto.response.JobCardResponse;
import com.microservice.kochimetro.jobCard.entity.JobCard;
import com.microservice.kochimetro.jobCard.mapper.JobCardMapper;
import com.microservice.kochimetro.jobCard.repository.JobCardRepository;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.repository.TrainRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class JobCardServiceImpl implements JobCardService {
    private JobCardRepository jobCardRepository;
    private TrainRepository trainRepository;

    public JobCardServiceImpl(JobCardRepository jobCardRepository, TrainRepository trainRepository) {
        this.jobCardRepository = jobCardRepository;
        this.trainRepository = trainRepository;
    }

    private JobCard findByIdHelper(UUID id) {
        JobCard jobCard = jobCardRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job card with the given id cannot be found: " + id));

        return jobCard;
    }

    @Override
    public JobCardResponse createJobCard(CreateJobCardRequest jobCardRequest) {
        Train train = trainRepository.findById(jobCardRequest.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + jobCardRequest.getTrainId()));

        //convert dto to entity
        JobCard jobCard = JobCardMapper.toEntity(jobCardRequest, train);

        JobCard savedJobCard = jobCardRepository.save(jobCard);

        //convert entity to dto
        return JobCardMapper.toResponse(savedJobCard);
    }

    @Override
    public JobCardResponse getJobCardById(UUID id) {
        JobCard job = jobCardRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Job Card with the given Id cannot be found: " + id)
        );

        return JobCardMapper.toResponse(job);
    }

    @Override
    public List<JobCardResponse> getJobCardByTrainId(UUID id) {
        // Check if the train exists
        trainRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Train not found with id: " + id));

        List<JobCard> jobCards = jobCardRepository.findByTrainId(id);

        return jobCards.stream()
                .map(JobCardMapper::toResponse)
                .toList();

    }

    @Override
    public List<JobCardResponse> getAllJobCards() {
        List<JobCard> jobs = jobCardRepository.findAll();

        return jobs.stream()
                .map(JobCardMapper::toResponse)
                .toList();
    }

    @Override
    public JobCardResponse updateJobCard(UUID id, UpdateJobCardRequest request) {
        JobCard jobCard = jobCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job Card cannot be found witht he given id: " + id));

        jobCard.setTitle(request.getTitle());
        jobCard.setDescription(request.getDescription());
        jobCard.setAssignedEngineer(request.getAssignedEngineer());
        jobCard.setMaintenanceType(request.getMaintenanceType());
        jobCard.setPriority(request.getPriority());
        jobCard.setEstimatedCompletion(request.getEstimatedCompletion());
        jobCard.setJobStatus(request.getJobStatus());

        JobCard updatedJobCard = jobCardRepository.save(jobCard);

        return JobCardMapper.toResponse(updatedJobCard);
    }

    @Override
    public void deleteJobCard(UUID id) {
        JobCard jobCard = jobCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job Card with the given id cannot be found: " + id));
        jobCardRepository.deleteById(id);
    }
}
