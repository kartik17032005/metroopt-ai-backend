package com.microservice.kochimetro.depot.service;

import com.microservice.kochimetro.depot.dto.request.CreateDepotResourceRequest;
import com.microservice.kochimetro.depot.dto.request.UpdateDepotResourceRequest;
import com.microservice.kochimetro.depot.dto.response.DepotResourceResponse;
import com.microservice.kochimetro.train.entity.enums.Depot;

import java.util.List;
import java.util.UUID;

public interface DepotResourceService {
    DepotResourceResponse create(CreateDepotResourceRequest request);
    DepotResourceResponse update(UUID id, UpdateDepotResourceRequest request);
    DepotResourceResponse getByDepot(Depot depot);
    List<DepotResourceResponse> getAll();
    void delete(UUID id);
}
