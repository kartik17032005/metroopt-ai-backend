package com.microservice.kochimetro.fitness.service;

import com.microservice.kochimetro.exception.BadRequestException;
import com.microservice.kochimetro.exception.ResourceNotFoundException;
import com.microservice.kochimetro.fitness.dto.request.CreateFitnessCertificateRequest;
import com.microservice.kochimetro.fitness.dto.request.UpdateFitnessCertificateRequest;
import com.microservice.kochimetro.fitness.dto.response.FitnessCertificateResponse;
import com.microservice.kochimetro.fitness.entity.FitnessCertificate;
import com.microservice.kochimetro.fitness.mapper.FitnessCertificateMapper;
import com.microservice.kochimetro.fitness.repository.FitnessCertificateRepository;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FitnessCertificateServiceImpl implements FitnessCertificateService {

    private final FitnessCertificateRepository fitnessCertificateRepository;
    private final TrainRepository trainRepository;

    public FitnessCertificateServiceImpl(FitnessCertificateRepository fitnessCertificateRepository, TrainRepository trainRepository) {
        this.fitnessCertificateRepository = fitnessCertificateRepository;
        this.trainRepository = trainRepository;
    }

    private FitnessCertificate findByIdHelper(UUID id) {
        return fitnessCertificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness certificate with the given id cannot be found: " + id));
    }

    @Override
    public FitnessCertificateResponse createFitnessCertificate(CreateFitnessCertificateRequest request) {
        if (fitnessCertificateRepository.existsByCertificateNumber(request.getCertificateNumber())) {
            throw new BadRequestException("Certificate number already exists: " + request.getCertificateNumber());
        }

        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + request.getTrainId()));

        FitnessCertificate certificate = FitnessCertificateMapper.toEntity(request, train);
        FitnessCertificate savedCertificate = fitnessCertificateRepository.save(certificate);

        return FitnessCertificateMapper.toResponse(savedCertificate);
    }

    @Override
    public FitnessCertificateResponse getFitnessCertificateById(UUID id) {
        FitnessCertificate certificate = findByIdHelper(id);
        return FitnessCertificateMapper.toResponse(certificate);
    }

    @Override
    public FitnessCertificateResponse getFitnessCertificateByCertificateNumber(String certificateNumber) {
        FitnessCertificate certificate = fitnessCertificateRepository.findByCertificateNumber(certificateNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness certificate with the given certificate number cannot be found: " + certificateNumber));
        return FitnessCertificateMapper.toResponse(certificate);
    }

    @Override
    public List<FitnessCertificateResponse> getFitnessCertificatesByTrainId(UUID trainId) {
        trainRepository.findById(trainId)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainId));

        List<FitnessCertificate> certificates = fitnessCertificateRepository.findByTrainId(trainId);
        return certificates.stream().map(FitnessCertificateMapper::toResponse).toList();
    }

    @Override
    public List<FitnessCertificateResponse> getAllFitnessCertificates() {
        List<FitnessCertificate> certificates = fitnessCertificateRepository.findAll();
        return certificates.stream().map(FitnessCertificateMapper::toResponse).toList();
    }

    @Override
    public FitnessCertificateResponse updateFitnessCertificate(UUID id, UpdateFitnessCertificateRequest request) {
        FitnessCertificate existingCertificate = findByIdHelper(id);

        if (!existingCertificate.getCertificateNumber().equals(request.getCertificateNumber()) &&
                fitnessCertificateRepository.existsByCertificateNumber(request.getCertificateNumber())) {
            throw new BadRequestException("Certificate number already exists: " + request.getCertificateNumber());
        }

        FitnessCertificateMapper.updateEntity(existingCertificate, request);
        FitnessCertificate updatedCertificate = fitnessCertificateRepository.save(existingCertificate);

        return FitnessCertificateMapper.toResponse(updatedCertificate);
    }

    @Override
    public void deleteFitnessCertificate(UUID id) {
        FitnessCertificate certificate = findByIdHelper(id);
        fitnessCertificateRepository.delete(certificate);
    }
}
