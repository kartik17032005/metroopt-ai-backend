package com.microservice.kochimetro.optimization;

import com.microservice.kochimetro.ai.integration.GeminiClient;
import com.microservice.kochimetro.ai.prompt.ExplanationPromptBuilder;
import com.microservice.kochimetro.ai.service.AIExplanationServiceImpl;
import com.microservice.kochimetro.depot.entity.DepotResource;
import com.microservice.kochimetro.optimization.dto.request.OptimizationRequest;
import com.microservice.kochimetro.optimization.dto.response.OptimizationResponse;
import com.microservice.kochimetro.optimization.dto.response.SelectedTrainResponse;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.AllocationStatus;
import com.microservice.kochimetro.optimization.orTools.builder.ObjectiveBuilder;
import com.microservice.kochimetro.optimization.orTools.constraint.*;
import com.microservice.kochimetro.optimization.orTools.objective.*;
import com.microservice.kochimetro.optimization.orTools.solver.BasicTrainSelectionSolver;
import com.microservice.kochimetro.train.entity.enums.Depot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OptimizationSolverTests {

    private BasicTrainSelectionSolver solver;
    private AIExplanationServiceImpl aiExplanationService;
    private GeminiClient geminiClient;
    private ObjectiveBuilder objectiveBuilder;

    @BeforeEach
    void setUp() {
        geminiClient = mock(GeminiClient.class);
        ExplanationPromptBuilder promptBuilder = new ExplanationPromptBuilder();
        aiExplanationService = new AIExplanationServiceImpl(promptBuilder, geminiClient);

        objectiveBuilder = new ObjectiveBuilder();
        MileageObjective mileageObjective = new MileageObjective(objectiveBuilder);
        BrandingObjective brandingObjective = new BrandingObjective(objectiveBuilder);
        StandbyPriorityObjective standbyPriorityObjective = new StandbyPriorityObjective(objectiveBuilder);
        InspectionPriorityObjective inspectionPriorityObjective = new InspectionPriorityObjective(objectiveBuilder);
        FleetUtilizationObjective fleetUtilizationObjective = new FleetUtilizationObjective(objectiveBuilder);

        FitnessConstraint fitnessConstraint = new FitnessConstraint();
        CleaningConstraint cleaningConstraint = new CleaningConstraint();
        MaintenanceConstraint maintenanceConstraint = new MaintenanceConstraint();
        StandbyConstraint standbyConstraint = new StandbyConstraint();
        InspectionConstraint inspectionConstraint = new InspectionConstraint();
        DepotResourceConstraint depotResourceConstraint = new DepotResourceConstraint();

        solver = new BasicTrainSelectionSolver(
                fitnessConstraint,
                cleaningConstraint,
                maintenanceConstraint,
                standbyConstraint,
                inspectionConstraint,
                mileageObjective,
                brandingObjective,
                standbyPriorityObjective,
                inspectionPriorityObjective,
                fleetUtilizationObjective,
                objectiveBuilder,
                depotResourceConstraint,
                aiExplanationService
        );
    }

    private List<TrainData> createSampleTrainData() {
        return List.of(
                TrainData.builder()
                        .trainId(UUID.randomUUID())
                        .trainNumber("TS-01")
                        .fitnessValid(true)
                        .cleaningCompleted(true)
                        .criticalMaintenance(false)
                        .mileage(12500)
                        .depot(Depot.MUTTOM)
                        .brandingActive(true)
                        .standbyEligible(true)
                        .inspectionDue(false)
                        .inspectionPriority(10)
                        .build(),
                TrainData.builder()
                        .trainId(UUID.randomUUID())
                        .trainNumber("TS-02")
                        .fitnessValid(true)
                        .cleaningCompleted(true)
                        .criticalMaintenance(false)
                        .mileage(3000)
                        .depot(Depot.MUTTOM)
                        .brandingActive(false)
                        .standbyEligible(true)
                        .inspectionDue(false)
                        .inspectionPriority(10)
                        .build(),
                TrainData.builder()
                        .trainId(UUID.randomUUID())
                        .trainNumber("TS-03")
                        .fitnessValid(true)
                        .cleaningCompleted(true)
                        .criticalMaintenance(false)
                        .mileage(8500)
                        .depot(Depot.ALUVA)
                        .brandingActive(false)
                        .standbyEligible(false)
                        .inspectionDue(true)
                        .inspectionPriority(80)
                        .build(),
                TrainData.builder()
                        .trainId(UUID.randomUUID())
                        .trainNumber("TS-04")
                        .fitnessValid(false)
                        .cleaningCompleted(false)
                        .criticalMaintenance(true)
                        .mileage(15000)
                        .depot(Depot.ALUVA)
                        .brandingActive(false)
                        .standbyEligible(false)
                        .inspectionDue(true)
                        .inspectionPriority(100)
                        .build()
        );
    }

    private Map<Depot, DepotResource> createSampleDepotResources() {
        Map<Depot, DepotResource> map = new HashMap<>();
        map.put(Depot.MUTTOM, DepotResource.builder()
                .depot(Depot.MUTTOM)
                .inspectionBayCapacity(5)
                .standbyTrackCapacity(5)
                .build());
        map.put(Depot.ALUVA, DepotResource.builder()
                .depot(Depot.ALUVA)
                .inspectionBayCapacity(5)
                .standbyTrackCapacity(5)
                .build());
        return map;
    }

    @Test
    void testOptimizationSolvesAndGeneratesContextualExplanations() {
        // When Gemini is null/unavailable, contextual fallback generates explanations
        when(geminiClient.generateContent(anyString())).thenReturn(null);

        OptimizationRequest request = OptimizationRequest.builder()
                .requiredOperatingTrains(1)
                .requiredStandByTrains(1)
                .requiredInspectionTrains(1)
                .algorithm("BALANCED")
                .build();

        OptimizationResponse response = solver.solve(
                request,
                createSampleTrainData(),
                createSampleDepotResources()
        );

        assertNotNull(response);
        assertEquals("OPTIMAL", response.getSolverStatus());
        assertFalse(response.getSelectedTrains().isEmpty());

        for (SelectedTrainResponse train : response.getSelectedTrains()) {
            assertNotNull(train.getExplanation(), "Explanation should not be null for " + train.getTrainNumber());
            assertFalse(train.getExplanation().isBlank(), "Explanation should not be blank for " + train.getTrainNumber());
        }
    }

    @Test
    void testOptimizationParsesGeminiAiExplanations() {
        when(geminiClient.generateContent(anyString())).thenReturn("""
                1. TS-01 - Train TS-01 is assigned to OPERATING because its active branding contract maximizes revenue.
                2. TS-02 - Train TS-02 is selected for STANDBY readiness due to low cumulative wear.
                3. TS-03 - Train TS-03 is routed to INSPECTION because its mileage exceeds the maintenance threshold.
                4. TS-04 - Train TS-04 is held at DEPOT due to open critical maintenance cards.
                """);

        OptimizationRequest request = OptimizationRequest.builder()
                .requiredOperatingTrains(1)
                .requiredStandByTrains(1)
                .requiredInspectionTrains(1)
                .algorithm("BRANDING_PRIORITY")
                .build();

        OptimizationResponse response = solver.solve(
                request,
                createSampleTrainData(),
                createSampleDepotResources()
        );

        assertNotNull(response);
        assertEquals("OPTIMAL", response.getSolverStatus());

        Optional<SelectedTrainResponse> ts01 = response.getSelectedTrains().stream()
                .filter(t -> t.getTrainNumber().equals("TS-01"))
                .findFirst();

        assertTrue(ts01.isPresent());
        assertTrue(ts01.get().getExplanation().contains("branding contract") || ts01.get().getExplanation().contains("revenue"));
    }

    @Test
    void testBrandingPriorityVsMileageBalancingStrategy() {
        when(geminiClient.generateContent(anyString())).thenReturn(null);

        List<TrainData> trains = createSampleTrainData();
        Map<Depot, DepotResource> depots = createSampleDepotResources();

        // 1. Branding priority should prefer TS-01 (branded) over TS-02 for operating
        OptimizationRequest brandingRequest = OptimizationRequest.builder()
                .requiredOperatingTrains(1)
                .requiredStandByTrains(0)
                .requiredInspectionTrains(0)
                .algorithm("BRANDING_PRIORITY")
                .build();

        OptimizationResponse brandingResponse = solver.solve(brandingRequest, trains, depots);
        SelectedTrainResponse operatingBranded = brandingResponse.getSelectedTrains().stream()
                .filter(t -> t.getAllocationStatus() == AllocationStatus.OPERATING)
                .findFirst().orElseThrow();
        assertEquals("TS-01", operatingBranded.getTrainNumber());

        // 2. Mileage balancing should prefer TS-02 (mileage 3000 km) over TS-01 (mileage 12500 km)
        OptimizationRequest mileageRequest = OptimizationRequest.builder()
                .requiredOperatingTrains(1)
                .requiredStandByTrains(0)
                .requiredInspectionTrains(0)
                .algorithm("MILEAGE_BALANCING")
                .build();

        OptimizationResponse mileageResponse = solver.solve(mileageRequest, trains, depots);
        SelectedTrainResponse operatingMileage = mileageResponse.getSelectedTrains().stream()
                .filter(t -> t.getAllocationStatus() == AllocationStatus.OPERATING)
                .findFirst().orElseThrow();
        assertEquals("TS-02", operatingMileage.getTrainNumber());
    }
}
