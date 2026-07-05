package com.microservice.kochimetro.depot.dto.response;

import com.microservice.kochimetro.train.entity.enums.Depot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepotResourceResponse {
    private UUID id;
    private Depot depot;
    private Integer inspectionBayCapacity;
    private Integer standbyTrackCapacity;
    private Integer stablingTrackCapacity;
    private Integer washingLineCapacity;
}
