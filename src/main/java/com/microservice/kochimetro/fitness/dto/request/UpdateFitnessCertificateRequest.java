package com.microservice.kochimetro.fitness.dto.request;

import com.microservice.kochimetro.fitness.entity.enums.CertificateStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFitnessCertificateRequest {

    @NotBlank(message = "Certificate number is required")
    private String certificateNumber;

    @NotNull(message = "Issue date is required")
    private Instant issuedAt;

    @NotNull(message = "Expiry date is required")
    private Instant expiresAt;

    @NotNull(message = "Certificate status is required")
    private CertificateStatus status;

    @NotBlank(message = "Issuer is required")
    private String issuedBy;
}
