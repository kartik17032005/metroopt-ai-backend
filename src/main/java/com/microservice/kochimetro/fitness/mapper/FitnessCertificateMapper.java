package com.microservice.kochimetro.fitness.mapper;

import com.microservice.kochimetro.fitness.dto.request.CreateFitnessCertificateRequest;
import com.microservice.kochimetro.fitness.dto.request.UpdateFitnessCertificateRequest;
import com.microservice.kochimetro.fitness.dto.response.FitnessCertificateResponse;
import com.microservice.kochimetro.fitness.entity.FitnessCertificate;
import com.microservice.kochimetro.train.entity.Train;

public final class FitnessCertificateMapper {

    private FitnessCertificateMapper() {
    }

    public static FitnessCertificate toEntity(CreateFitnessCertificateRequest request, Train train) {
        return FitnessCertificate.builder()
                .certificateNumber(request.getCertificateNumber())
                .train(train)
                .issuedAt(request.getIssuedAt())
                .expiresAt(request.getExpiresAt())
                .status(request.getStatus())
                .issuedBy(request.getIssuedBy())
                .build();
    }

    public static FitnessCertificateResponse toResponse(FitnessCertificate certificate) {
        return FitnessCertificateResponse.builder()
                .id(certificate.getId())
                .certificateNumber(certificate.getCertificateNumber())
                .trainId(certificate.getTrain().getId())
                .trainNumber(certificate.getTrain().getTrainNumber())
                .issuedAt(certificate.getIssuedAt())
                .expiresAt(certificate.getExpiresAt())
                .status(certificate.getStatus())
                .issuedBy(certificate.getIssuedBy())
                .createdAt(certificate.getCreatedAt())
                .updatedAt(certificate.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FitnessCertificate certificate, UpdateFitnessCertificateRequest request) {
        if (request.getCertificateNumber() != null) {
            certificate.setCertificateNumber(request.getCertificateNumber());
        }
        if (request.getIssuedAt() != null) {
            certificate.setIssuedAt(request.getIssuedAt());
        }
        if (request.getExpiresAt() != null) {
            certificate.setExpiresAt(request.getExpiresAt());
        }
        if (request.getStatus() != null) {
            certificate.setStatus(request.getStatus());
        }
        if (request.getIssuedBy() != null) {
            certificate.setIssuedBy(request.getIssuedBy());
        }
    }
}
