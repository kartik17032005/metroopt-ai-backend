package com.microservice.kochimetro.branding.dto.request;

import com.microservice.kochimetro.branding.entity.enums.BrandingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBrandingContractRequest {

    @NotNull(message = "Train ID is required")
    private UUID trainId;

    @NotBlank(message = "Advertiser name is required")
    private String advertiser;

    @NotBlank(message = "Campaign name is required")
    private String campaignName;

    @NotNull(message = "Start date is required")
    private Instant startDate;

    @NotNull(message = "End date is required")
    private Instant endDate;

    @NotNull(message = "Branding status is required")
    private BrandingStatus status;
}
