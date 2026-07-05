package com.microservice.kochimetro.depot.mapper;

import com.microservice.kochimetro.depot.dto.request.CreateDepotResourceRequest;
import com.microservice.kochimetro.depot.dto.request.UpdateDepotResourceRequest;
import com.microservice.kochimetro.depot.dto.response.DepotResourceResponse;
import com.microservice.kochimetro.depot.entity.DepotResource;

public final class DepotResourceMapper {

    private DepotResourceMapper() {
    }

    public static DepotResource toEntity(CreateDepotResourceRequest request) {
        return DepotResource.builder()
                .depot(request.getDepot())
                .inspectionBayCapacity(request.getInspectionBayCapacity())
                .standbyTrackCapacity(request.getStandbyTrackCapacity())
                .stablingTrackCapacity(request.getStablingTrackCapacity())
                .washingLineCapacity(request.getWashingLineCapacity())
                .build();
    }

    public static DepotResourceResponse toResponse(DepotResource entity) {
        return DepotResourceResponse.builder()
                .id(entity.getId())
                .depot(entity.getDepot())
                .inspectionBayCapacity(entity.getInspectionBayCapacity())
                .standbyTrackCapacity(entity.getStandbyTrackCapacity())
                .stablingTrackCapacity(entity.getStablingTrackCapacity())
                .washingLineCapacity(entity.getWashingLineCapacity())
                .build();
    }

    public static void updateEntity(DepotResource entity, UpdateDepotResourceRequest request) {
        if (request.getInspectionBayCapacity() != null) {
            entity.setInspectionBayCapacity(request.getInspectionBayCapacity());
        }
        if (request.getStandbyTrackCapacity() != null) {
            entity.setStandbyTrackCapacity(request.getStandbyTrackCapacity());
        }
        if (request.getStablingTrackCapacity() != null) {
            entity.setStablingTrackCapacity(request.getStablingTrackCapacity());
        }
        if (request.getWashingLineCapacity() != null) {
            entity.setWashingLineCapacity(request.getWashingLineCapacity());
        }
    }
}
