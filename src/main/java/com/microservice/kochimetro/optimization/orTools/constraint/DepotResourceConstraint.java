package com.microservice.kochimetro.optimization.orTools.constraint;

import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.LinearExpr;
import com.microservice.kochimetro.depot.entity.DepotResource;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.train.entity.enums.Depot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DepotResourceConstraint {

    public void apply(
            CpModel cpModel,
            BoolVar[] standbyVariables,
            BoolVar[] inspectionVariables,
            List<TrainData> trainDataList,
            Map<Depot, DepotResource> depotResources
    ) {

        for (Map.Entry<Depot, DepotResource> entry : depotResources.entrySet()) {

            Depot depot = entry.getKey();
            DepotResource resource = entry.getValue();

            List<BoolVar> standbyVars = new ArrayList<>();
            List<BoolVar> inspectionVars = new ArrayList<>();

            for (int i = 0; i < trainDataList.size(); i++) {

                if (trainDataList.get(i).getDepot() == depot) {

                    standbyVars.add(standbyVariables[i]);
                    inspectionVars.add(inspectionVariables[i]);
                }
            }

            cpModel.addLessOrEqual(
                    LinearExpr.sum(
                            standbyVars.toArray(new BoolVar[0])
                    ),
                    resource.getStandbyTrackCapacity()
            );

            cpModel.addLessOrEqual(
                    LinearExpr.sum(
                            inspectionVars.toArray(new BoolVar[0])
                    ),
                    resource.getInspectionBayCapacity()
            );
        }
    }
}