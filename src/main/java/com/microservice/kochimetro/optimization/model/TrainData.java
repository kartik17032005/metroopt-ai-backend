package com.microservice.kochimetro.optimization.model;

import com.microservice.kochimetro.branding.entity.enums.BrandingStatus;
import com.microservice.kochimetro.train.entity.enums.Depot;
import lombok.*;

import java.util.UUID;


/*
    my train data is spread across multiple files like branding,
    fitness, mileage but solver needs something simple so that's
    why we created train data, because it has everything
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainData {
    private UUID trainId;
    private String trainNumber;
    private boolean fitnessValid;
    private boolean cleaningCompleted;
    private boolean criticalMaintenance;
    private int mileage;
    private BrandingStatus brandingStatus;
    private boolean brandingActive;
    private boolean standbyEligible;
    private boolean inspectionDue;
    private int inspectionPriority;
    private     Depot depot;

    public boolean isBranded(){
        return brandingStatus == BrandingStatus.ACTIVE;
    }
}
