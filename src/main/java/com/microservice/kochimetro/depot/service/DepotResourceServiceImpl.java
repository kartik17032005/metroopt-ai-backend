package com.microservice.kochimetro.depot.service;

import com.microservice.kochimetro.depot.dto.request.CreateDepotResourceRequest;
import com.microservice.kochimetro.depot.dto.request.UpdateDepotResourceRequest;
import com.microservice.kochimetro.depot.dto.response.DepotResourceResponse;
import com.microservice.kochimetro.depot.entity.DepotResource;
import com.microservice.kochimetro.depot.mapper.DepotResourceMapper;
import com.microservice.kochimetro.depot.repository.DepotResourceRepository;
import com.microservice.kochimetro.exception.DuplicateResourceException;
import com.microservice.kochimetro.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of {@link DepotResourceService} handling CRUD operations for depot resources.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DepotResourceServiceImpl implements DepotResourceService {

    private final DepotResourceRepository depotResourceRepository;

    @Override
    @Transactional
    public DepotResourceResponse create(CreateDepotResourceRequest request) {
        // Ensure depot uniqueness
        depotResourceRepository.findByDepot(request.getDepot())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Depot resource for " + request.getDepot() + " already exists");
                });
        DepotResource entity = DepotResourceMapper.toEntity(request);
        DepotResource saved = depotResourceRepository.save(entity);
        log.info("Created depot resource with id {} for depot {}", saved.getId(), saved.getDepot());
        return DepotResourceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public DepotResourceResponse update(UUID id, UpdateDepotResourceRequest request) {
        DepotResource existing = depotResourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DepotResource", "id", id));
        DepotResourceMapper.updateEntity(existing, request);
        DepotResource updated = depotResourceRepository.save(existing);
        log.info("Updated depot resource {}", id);
        return DepotResourceMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepotResourceResponse> getAll() {
        return depotResourceRepository.findAll().stream()
                .map(DepotResourceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DepotResourceResponse getByDepot(com.microservice.kochimetro.train.entity.enums.Depot depot) {
        DepotResource entity = depotResourceRepository.findByDepot(depot)
                .orElseThrow(() -> new ResourceNotFoundException("DepotResource", "depot", depot));
        return DepotResourceMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        DepotResource entity = depotResourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DepotResource", "id", id));
        depotResourceRepository.delete(entity);
        log.info("Deleted depot resource {}", id);
    }
}
