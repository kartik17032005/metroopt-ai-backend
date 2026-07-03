package com.microservice.kochimetro.fitness.service;

import com.microservice.kochimetro.fitness.dto.request.CreateFitnessCertificateRequest;
import com.microservice.kochimetro.fitness.dto.request.UpdateFitnessCertificateRequest;
import com.microservice.kochimetro.fitness.dto.response.FitnessCertificateResponse;

import java.util.List;
import java.util.UUID;

public interface FitnessCertificateService {
    FitnessCertificateResponse createFitnessCertificate(CreateFitnessCertificateRequest request);
    FitnessCertificateResponse getFitnessCertificateById(UUID id);
    FitnessCertificateResponse getFitnessCertificateByCertificateNumber(String certificateNumber);
    List<FitnessCertificateResponse> getFitnessCertificatesByTrainId(UUID trainId);
    List<FitnessCertificateResponse> getAllFitnessCertificates();
    FitnessCertificateResponse updateFitnessCertificate(UUID id, UpdateFitnessCertificateRequest request);
    void deleteFitnessCertificate(UUID id);
}
