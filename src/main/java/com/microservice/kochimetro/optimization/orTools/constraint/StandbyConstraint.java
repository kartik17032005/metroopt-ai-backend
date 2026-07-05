package com.microservice.kochimetro.optimization.orTools.constraint;

import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.LinearExpr;
import com.microservice.kochimetro.optimization.model.TrainData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Constraint that enforces standby eligibility and exact standby count.
 */
@Component
public class StandbyConstraint {
    public void apply(
            CpModel model,
            BoolVar[] operating,
            BoolVar[] standby,
            List<TrainData> trains,
            int standbyCount
    ){
        for (int i = 0; i < trains.size(); i++) {

            TrainData train = trains.get(i);

            // Ineligible trains cannot become standby
            if (!train.isStandbyEligible()) {
                model.addEquality(standby[i], 0);
            }
        }

        // Exactly N standby trains
        model.addEquality(
                LinearExpr.sum(standby),
                standbyCount
        );
    }
}
