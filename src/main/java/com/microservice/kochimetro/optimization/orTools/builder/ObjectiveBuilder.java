package com.microservice.kochimetro.optimization.orTools.builder;

import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.LinearExpr;
import com.google.ortools.sat.LinearExprBuilder;
import com.microservice.kochimetro.optimization.orTools.config.OptimizationWeights;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ObjectiveBuilder {

    private final LinearExprBuilder objective =
            LinearExpr.newBuilder();

    // Dynamic weight map – allows runtime adjustment without hard‑coding order
    private final Map<ObjectiveWeight, Long> weightMap = new HashMap<>();

    public ObjectiveBuilder() {
        // Initialize with the default static weights
        weightMap.put(ObjectiveWeight.MILEAGE, OptimizationWeights.MILEAGE_WEIGHT);
        weightMap.put(ObjectiveWeight.BRANDING, OptimizationWeights.BRANDING_WEIGHT);
        weightMap.put(ObjectiveWeight.INSPECTION, OptimizationWeights.INSPECTION_PRIORITY_WEIGHT);
        weightMap.put(ObjectiveWeight.STANDBY, OptimizationWeights.STANDBY_PRIORITY_WEIGHT);
        weightMap.put(ObjectiveWeight.FLEET_UTILIZATION, OptimizationWeights.FLEET_UTILIZATION_WEIGHT);
    }

    public LinearExprBuilder getObjective() {
        return objective;
    }

    public void minimize(CpModel cpModel) {
        cpModel.minimize(objective);
    }

    public void maximize(CpModel cpModel) {
        cpModel.maximize(objective);
    }

    /**
     * Add a term using a dynamic weight key. The final coefficient is
     * {@code base * weightMap.get(weightKey)}. If the key is missing, a weight of 1L is used.
     */
    public void addWeightedTerm(BoolVar var, ObjectiveWeight weightKey, long base) {
        Objects.requireNonNull(var);
        Objects.requireNonNull(weightKey);
        long weight = weightMap.getOrDefault(weightKey, 1L);
        objective.addTerm(var, base * weight);
    }

    /** Override a weight value at runtime. */
    public void setWeight(ObjectiveWeight weightKey, long weight) {
        weightMap.put(weightKey, weight);
    }

    /** Enum representing the supported objective weight categories. */
    public enum ObjectiveWeight {
        MILEAGE,
        BRANDING,
        INSPECTION,
        STANDBY,
        FLEET_UTILIZATION
    }
}