package com.microservice.kochimetro.fitness.dto.response;

import com.microservice.kochimetro.fitness.entity.enums.CertificateStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FitnessCertificateResponse {
    private UUID id;
    private String certificateNumber;
    private UUID trainId;
    private String trainNumber;
    private Instant issuedAt;
    private Instant expiresAt;
    private CertificateStatus status;
    private String issuedBy;
    private Instant createdAt;
    private Instant updatedAt;
}
