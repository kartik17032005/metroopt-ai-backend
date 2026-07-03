package com.microservice.kochimetro.branding.mapper;

import com.microservice.kochimetro.branding.dto.request.CreateBrandingContractRequest;
import com.microservice.kochimetro.branding.dto.request.UpdateBrandingContractRequest;
import com.microservice.kochimetro.branding.dto.response.BrandingContractResponse;
import com.microservice.kochimetro.branding.entity.BrandingContract;
import com.microservice.kochimetro.train.entity.Train;

public final class BrandingContractMapper {

    private BrandingContractMapper() {
    }

    public static BrandingContract toEntity(CreateBrandingContractRequest request, Train train) {
        return BrandingContract.builder()
                .train(train)
                .advertiser(request.getAdvertiser())
                .campaignName(request.getCampaignName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .build();
    }

    public static BrandingContractResponse toResponse(BrandingContract contract) {
        return BrandingContractResponse.builder()
                .id(contract.getId())
                .trainId(contract.getTrain().getId())
                .trainNumber(contract.getTrain().getTrainNumber())
                .advertiser(contract.getAdvertiser())
                .campaignName(contract.getCampaignName())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .status(contract.getStatus())
                .createdAt(contract.getCreatedAt())
                .updatedAt(contract.getUpdatedAt())
                .build();
    }

    public static void updateEntity(BrandingContract contract, UpdateBrandingContractRequest request) {
        if (request.getAdvertiser() != null) {
            contract.setAdvertiser(request.getAdvertiser());
        }
        if (request.getCampaignName() != null) {
            contract.setCampaignName(request.getCampaignName());
        }
        if (request.getStartDate() != null) {
            contract.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            contract.setEndDate(request.getEndDate());
        }
        if (request.getStatus() != null) {
            contract.setStatus(request.getStatus());
        }
    }
}
