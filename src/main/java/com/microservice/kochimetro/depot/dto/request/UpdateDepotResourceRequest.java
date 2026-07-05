package com.microservice.kochimetro.depot.dto.request;

import com.microservice.kochimetro.train.entity.enums.Depot;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDepotResourceRequest {
    @Min(0)
    private Integer inspectionBayCapacity;
    @Min(0)
    private Integer standbyTrackCapacity;
    @Min(0)
    private Integer stablingTrackCapacity;
    @Min(0)
    private Integer washingLineCapacity;
}
