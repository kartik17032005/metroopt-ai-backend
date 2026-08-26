package com.microservice.kochimetro.optimization.orTools.objective;

import com.google.ortools.sat.BoolVar;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.builder.ObjectiveBuilder;
import com.microservice.kochimetro.optimization.orTools.config.OptimizationWeights;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Objective that prefers lower‑mileage trains for standby allocation.
 *
 * It adds a penalty proportional to the train mileage for each train that is
 * assigned to standby. Lower mileage yields a smaller penalty, encouraging
 * the optimizer to select low‑mileage standby‑eligible trains.
 */
@Component
@RequiredArgsConstructor
public class StandbyPriorityObjective {

    private final ObjectiveBuilder objectiveBuilder;

    public void apply(BoolVar[] standbyVariables,
                      List<TrainData> trainDataList) {
        for (int i = 0; i < trainDataList.size(); i++) {
            TrainData train = trainDataList.get(i);
            if (!train.isStandbyEligible()) {
                continue; // Ineligible trains are handled by constraints
            }
            // Add positive penalty (minimize) for mileage in standby
            objectiveBuilder.addWeightedTerm(standbyVariables[i], ObjectiveBuilder.ObjectiveWeight.STANDBY, train.getMileage());
        }
    }
}
