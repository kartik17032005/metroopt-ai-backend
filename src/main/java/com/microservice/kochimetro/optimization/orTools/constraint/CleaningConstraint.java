package com.microservice.kochimetro.optimization.orTools.constraint;

import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.microservice.kochimetro.optimization.model.TrainData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CleaningConstraint {

    public void apply(
            CpModel cpModel,
            BoolVar[] trainVariables,
            List<TrainData> trainDataList
    ) {

        for (int i = 0; i < trainDataList.size(); i++) {

            TrainData train = trainDataList.get(i);

            if (!train.isCleaningCompleted()) {
                cpModel.addEquality(trainVariables[i], 0);
            }
        }
    }
}
