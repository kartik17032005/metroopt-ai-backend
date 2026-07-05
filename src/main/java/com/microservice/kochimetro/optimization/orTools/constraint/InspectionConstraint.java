package com.microservice.kochimetro.optimization.orTools.constraint;

import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.LinearExpr;
import com.microservice.kochimetro.optimization.model.TrainData;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Constraint that enforces inspection eligibility and exact inspection count.
 */
@Component
public class InspectionConstraint {

    public void apply(
            CpModel cpModel,
            BoolVar[] operating,
            BoolVar[] standBy,
            BoolVar[] inspection,
            List<TrainData> trains,
            int inspectionCount
    ) {
        for (int i = 0; i < trains.size(); i++) {
            TrainData train = trains.get(i);

            // A train can enter the inspection bay if it is due for inspection,
            // has critical maintenance, or does not have a valid fitness certificate.
            boolean eligibleForInspection = train.isInspectionDue() 
                    || train.isCriticalMaintenance() 
                    || !train.isFitnessValid();

            if (!eligibleForInspection) {
                cpModel.addEquality(inspection[i], 0);
            }
        }

        // Exactly N trains must be sent for inspection
        cpModel.addEquality(
                LinearExpr.sum(inspection),
                inspectionCount
        );
    }
}

