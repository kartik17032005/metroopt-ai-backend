package com.microservice.kochimetro.branding.dto.response;

import com.microservice.kochimetro.branding.entity.enums.BrandingStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandingContractResponse {
    private UUID id;
    private UUID trainId;
    private String trainNumber;
    private String advertiser;
    private String campaignName;
    private Instant startDate;
    private Instant endDate;
    private BrandingStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
