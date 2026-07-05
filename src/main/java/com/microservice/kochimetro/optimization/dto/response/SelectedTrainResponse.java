package com.microservice.kochimetro.optimization.dto.response;

import com.microservice.kochimetro.optimization.orTools.AllocationStatus;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectedTrainResponse {
    private UUID trainId;
    private String trainNumber;
    private AllocationStatus allocationStatus;
    private String explanation;
}
