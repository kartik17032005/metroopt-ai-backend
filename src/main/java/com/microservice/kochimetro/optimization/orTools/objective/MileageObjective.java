package com.microservice.kochimetro.optimization.orTools.objective;

import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.LinearExpr;
import com.google.ortools.sat.LinearExprBuilder;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.builder.ObjectiveBuilder;
import com.microservice.kochimetro.optimization.orTools.config.OptimizationWeights;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MileageObjective {

    private final ObjectiveBuilder objectiveBuilder;

    public void apply(
                      BoolVar[] trainVariables,
                      List<TrainData> trainDataList
    ) {
        LinearExprBuilder objective = LinearExpr.newBuilder();

        for (int i = 0; i < trainDataList.size(); i++) {
            objectiveBuilder.addWeightedTerm(
                    trainVariables[i],
                    ObjectiveBuilder.ObjectiveWeight.MILEAGE,
                    trainDataList.get(i).getMileage()
            );
        }

    }
}
