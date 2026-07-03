package com.microservice.kochimetro.branding.service;

import com.microservice.kochimetro.branding.dto.request.CreateBrandingContractRequest;
import com.microservice.kochimetro.branding.dto.request.UpdateBrandingContractRequest;
import com.microservice.kochimetro.branding.dto.response.BrandingContractResponse;

import java.util.List;
import java.util.UUID;

public interface BrandingContractService {
    BrandingContractResponse createBrandingContract(CreateBrandingContractRequest request);
    BrandingContractResponse getBrandingContractById(UUID id);
    List<BrandingContractResponse> getBrandingContractsByTrainId(UUID trainId);
    List<BrandingContractResponse> getAllBrandingContracts();
    BrandingContractResponse updateBrandingContract(UUID id, UpdateBrandingContractRequest request);
    void deleteBrandingContract(UUID id);
}
