package com.microservice.kochimetro.optimization.orTools.objective;

import com.google.ortools.sat.BoolVar;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.builder.ObjectiveBuilder;
import com.microservice.kochimetro.optimization.orTools.builder.ObjectiveBuilder.ObjectiveWeight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Objective that encourages optimal fleet utilization.
 *
 * Currently uses a placeholder base factor of {@code 1L}. You can replace the base
 * with a more meaningful metric (e.g., train capacity, current load, etc.) when the
 * model data becomes available.
 */
@Component
@RequiredArgsConstructor
public class FleetUtilizationObjective {

    private final ObjectiveBuilder objectiveBuilder;

    public void apply(BoolVar[] trainVariables, List<TrainData> trainDataList) {
        for (int i = 0; i < trainDataList.size(); i++) {
            // Add a weighted term for fleet utilization. The actual metric can be
            // substituted for the constant "1L" when appropriate.
            objectiveBuilder.addWeightedTerm(trainVariables[i], ObjectiveWeight.FLEET_UTILIZATION, 1L);
        }
    }
}
