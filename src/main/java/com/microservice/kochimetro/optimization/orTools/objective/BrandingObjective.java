package com.microservice.kochimetro.optimization.orTools.objective;

import com.google.ortools.sat.BoolVar;
import com.microservice.kochimetro.branding.entity.enums.BrandingStatus;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.builder.ObjectiveBuilder;
import com.microservice.kochimetro.optimization.orTools.config.OptimizationWeights;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandingObjective {

    private final ObjectiveBuilder objectiveBuilder;

    public BrandingObjective(ObjectiveBuilder objectiveBuilder) {
        this.objectiveBuilder = objectiveBuilder;
    }

    public void apply(
            BoolVar[] trainVariables,
            List<TrainData> trainDataList
    ) {

        for (int i = 0; i < trainDataList.size(); i++) {

            TrainData train = trainDataList.get(i);

            if (train.isBranded()) {

                objectiveBuilder.getObjective().addTerm(
                        trainVariables[i],
                        -OptimizationWeights.BRANDING_BONUS
                );
            }
        }
    }
}