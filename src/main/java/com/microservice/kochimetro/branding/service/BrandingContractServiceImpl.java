package com.microservice.kochimetro.branding.service;

import com.microservice.kochimetro.branding.dto.request.CreateBrandingContractRequest;
import com.microservice.kochimetro.branding.dto.request.UpdateBrandingContractRequest;
import com.microservice.kochimetro.branding.dto.response.BrandingContractResponse;
import com.microservice.kochimetro.branding.entity.BrandingContract;
import com.microservice.kochimetro.branding.mapper.BrandingContractMapper;
import com.microservice.kochimetro.branding.repository.BrandingContractRepository;
import com.microservice.kochimetro.exception.ResourceNotFoundException;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BrandingContractServiceImpl implements BrandingContractService {

    private final BrandingContractRepository brandingContractRepository;
    private final TrainRepository trainRepository;

    public BrandingContractServiceImpl(BrandingContractRepository brandingContractRepository, TrainRepository trainRepository) {
        this.brandingContractRepository = brandingContractRepository;
        this.trainRepository = trainRepository;
    }

    private BrandingContract findByIdHelper(UUID id) {
        return brandingContractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branding contract with the given id cannot be found: " + id));
    }

    @Override
    public BrandingContractResponse createBrandingContract(CreateBrandingContractRequest request) {
        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + request.getTrainId()));

        BrandingContract contract = BrandingContractMapper.toEntity(request, train);
        BrandingContract savedContract = brandingContractRepository.save(contract);

        return BrandingContractMapper.toResponse(savedContract);
    }

    @Override
    public BrandingContractResponse getBrandingContractById(UUID id) {
        BrandingContract contract = findByIdHelper(id);
        return BrandingContractMapper.toResponse(contract);
    }

    @Override
    public List<BrandingContractResponse> getBrandingContractsByTrainId(UUID trainId) {
        trainRepository.findById(trainId)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainId));

        List<BrandingContract> contracts = brandingContractRepository.findByTrainId(trainId);
        return contracts.stream().map(BrandingContractMapper::toResponse).toList();
    }

    @Override
    public List<BrandingContractResponse> getAllBrandingContracts() {
        List<BrandingContract> contracts = brandingContractRepository.findAll();
        return contracts.stream().map(BrandingContractMapper::toResponse).toList();
    }

    @Override
    public BrandingContractResponse updateBrandingContract(UUID id, UpdateBrandingContractRequest request) {
        BrandingContract existingContract = findByIdHelper(id);

        BrandingContractMapper.updateEntity(existingContract, request);
        BrandingContract updatedContract = brandingContractRepository.save(existingContract);

        return BrandingContractMapper.toResponse(updatedContract);
    }

    @Override
    public void deleteBrandingContract(UUID id) {
        BrandingContract contract = findByIdHelper(id);
        brandingContractRepository.delete(contract);
    }
}
