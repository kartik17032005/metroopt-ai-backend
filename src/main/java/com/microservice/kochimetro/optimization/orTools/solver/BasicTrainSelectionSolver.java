package com.microservice.kochimetro.optimization.orTools.solver;

import com.google.ortools.Loader;
import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.LinearExpr;
import com.microservice.kochimetro.depot.entity.DepotResource;
import com.microservice.kochimetro.optimization.dto.request.OptimizationRequest;
import com.microservice.kochimetro.optimization.dto.response.OptimizationResponse;
import com.microservice.kochimetro.optimization.dto.response.SelectedTrainResponse;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.AllocationStatus;
import com.microservice.kochimetro.optimization.orTools.builder.ObjectiveBuilder;
import com.microservice.kochimetro.optimization.orTools.constraint.*;
import com.microservice.kochimetro.optimization.orTools.objective.*;
import com.microservice.kochimetro.train.entity.enums.Depot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BasicTrainSelectionSolver {

    static{
        Loader.loadNativeLibraries();
    }

    private final FitnessConstraint fitnessConstraint;
    private final CleaningConstraint cleaningConstraint;
    private final MaintenanceConstraint maintenanceConstraint;
    private final StandbyConstraint standbyConstraint;
    private final InspectionConstraint inspectionConstraint;
    private final MileageObjective mileageObjective;
    private final BrandingObjective brandingObjective;
    private final StandbyPriorityObjective standbyPriorityObjective;
    private final InspectionPriorityObjective inspectionPriorityObjective;
    private final FleetUtilizationObjective fleetUtilizationObjective;
    private final ObjectiveBuilder objectiveBuilder;
    private final DepotResourceConstraint depotResourceConstraint;

    public OptimizationResponse solve(
            OptimizationRequest request,
            List<TrainData> trainDataList,
            Map<Depot, DepotResource> depotResources
    ) {

        CpModel cpModel = new CpModel();

        int numberOfTrains = trainDataList.size();

        //--------------------------------------------------
        // Decision Variables
        //--------------------------------------------------

        BoolVar[] trainVariables = new BoolVar[numberOfTrains];
        BoolVar[] standbyVariables = new BoolVar[numberOfTrains];
        BoolVar[] inspectionVariables = new BoolVar[numberOfTrains];

        for (int i = 0; i < numberOfTrains; i++) {

            String trainNumber = trainDataList.get(i).getTrainNumber();

            trainVariables[i] =
                    cpModel.newBoolVar(trainNumber + "_RUN");

            standbyVariables[i] =
                    cpModel.newBoolVar(trainNumber + "_STANDBY");

            inspectionVariables[i] =
                    cpModel.newBoolVar(trainNumber + "_INSPECTION");
        }

        // -------------------------------------------------
        // One train can have only one allocation
        // -------------------------------------------------
        for (int i = 0; i < numberOfTrains; i++) {

            cpModel.addLessOrEqual(
                    LinearExpr.sum(new BoolVar[]{
                            trainVariables[i],
                            standbyVariables[i],
                            inspectionVariables[i]
                    }),
                    1
            );
        }

        //--------------------------------------------------
        // Hard Constraints
        //--------------------------------------------------

        fitnessConstraint.apply(
                cpModel,
                trainVariables,
                trainDataList
        );

        cleaningConstraint.apply(
                cpModel,
                trainVariables,
                trainDataList
        );

        maintenanceConstraint.apply(
                cpModel,
                trainVariables,
                trainDataList
        );

        standbyConstraint.apply(
                cpModel,
                trainVariables,
                standbyVariables,
                trainDataList,
                request.getRequiredStandByTrains()
        );

        inspectionConstraint.apply(
                cpModel,
                trainVariables,
                standbyVariables,
                inspectionVariables,
                trainDataList,
                request.getRequiredInspectionTrains()
        );

        depotResourceConstraint.apply(
                cpModel,
                standbyVariables,
                inspectionVariables,
                trainDataList,
                depotResources
        );

        //--------------------------------------------------
        // Operating Trains
        //--------------------------------------------------

        cpModel.addEquality(
                LinearExpr.sum(trainVariables),
                request.getRequiredOperatingTrains()
        );

        //--------------------------------------------------
        // Objective
        //--------------------------------------------------

        mileageObjective.apply(
                trainVariables,
                trainDataList
        );

        brandingObjective.apply(
                trainVariables,
                trainDataList
        );

        objectiveBuilder.minimize(cpModel);

        //--------------------------------------------------
        // Solve
        //--------------------------------------------------

        System.out.println("========== TRAIN DATA ==========");

        for (TrainData train : trainDataList) {

            System.out.println(
                    train.getTrainNumber()
                            + " | Fitness=" + train.isFitnessValid()
                            + " | Cleaning=" + train.isCleaningCompleted()
                            + " | Critical=" + train.isCriticalMaintenance()
                            + " | Standby=" + train.isStandbyEligible()
                            + " | Inspection=" + train.isInspectionDue()
                            + " | Mileage=" + train.getMileage()
                            + " | Depot=" + train.getDepot()
            );
        }

        CpSolver solver = new CpSolver();

        CpSolverStatus status = solver.solve(cpModel);

        List<SelectedTrainResponse> selectedTrains = new ArrayList<>();

        if (status == CpSolverStatus.OPTIMAL
                || status == CpSolverStatus.FEASIBLE) {

            for (int i = 0; i < numberOfTrains; i++) {

                TrainData train = trainDataList.get(i);

                boolean operating =
                        solver.value(trainVariables[i]) == 1;

                boolean standby =
                        solver.value(standbyVariables[i]) == 1;

                boolean inspection =
                        solver.value(inspectionVariables[i]) == 1;

                AllocationStatus allocationStatus;

                if (solver.value(trainVariables[i]) == 1) {
                    allocationStatus = AllocationStatus.OPERATING;
                } else if (solver.value(standbyVariables[i]) == 1) {
                    allocationStatus = AllocationStatus.STANDBY;
                } else if (solver.value(inspectionVariables[i]) == 1) {
                    allocationStatus = AllocationStatus.INSPECTION;
                } else {
                    allocationStatus = AllocationStatus.DEPOT;
                }

                selectedTrains.add(
                        SelectedTrainResponse.builder()
                                .trainId(train.getTrainId())
                                .trainNumber(train.getTrainNumber())
                                .allocationStatus(allocationStatus)
                                .build()
                );
            }

            return OptimizationResponse.builder()
                    .solverStatus(status.name())
                    .totalSelected(
                            request.getRequiredOperatingTrains()
                    )
                    .selectedTrains(selectedTrains)
                    .build();
        }

        return OptimizationResponse.builder()
                .solverStatus(status.name())
                .totalSelected(0)
                .selectedTrains(List.of())
                .build();
    }
}