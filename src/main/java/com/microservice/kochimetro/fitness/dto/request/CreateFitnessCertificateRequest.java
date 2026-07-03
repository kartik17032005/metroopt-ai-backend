package com.microservice.kochimetro.fitness.dto.request;

import com.microservice.kochimetro.fitness.entity.enums.CertificateStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFitnessCertificateRequest {

    @NotBlank(message = "Certificate number is required")
    @Size(min = 3, max = 50, message = "Certificate number must be between 3 and 50 characters")
    private String certificateNumber;

    @NotNull(message = "Train ID is required")
    private UUID trainId;

    @NotNull(message = "Issue date is required")
    private Instant issuedAt;

    @NotNull(message = "Expiry date is required")
    private Instant expiresAt;

    @NotNull(message = "Certificate status is required")
    private CertificateStatus status;

    @NotBlank(message = "Issuer is required")
    private String issuedBy;
}
