package com.microservice.kochimetro.optimization.orTools.objective;

import com.google.ortools.sat.BoolVar;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.builder.ObjectiveBuilder;
import com.microservice.kochimetro.optimization.orTools.config.OptimizationWeights;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Objective that prefers trains with higher inspection priority for inspection allocation.
 *
 * Since the solver minimizes the objective, we add a *negative* term proportional to the
 * inspection priority, effectively rewarding higher priority values.
 */
@Component
@RequiredArgsConstructor
public class InspectionPriorityObjective {

    private final ObjectiveBuilder objectiveBuilder;

    public void apply(BoolVar[] inspectionVariables,
                      List<TrainData> trainDataList) {  
        for (int i = 0; i < trainDataList.size(); i++) {
            TrainData train = trainDataList.get(i);
            // Only trains that are due for inspection are considered by the constraint.
            // If a train is not due, the inspection variable is forced to 0, so adding a term has no effect.
            long weight = train.getInspectionPriority() * OptimizationWeights.INSPECTION_PRIORITY_WEIGHT;
            // Negative weight to reward higher priority (minimization)
            objectiveBuilder.getObjective().addTerm(inspectionVariables[i], -weight);
        }
    }
}
