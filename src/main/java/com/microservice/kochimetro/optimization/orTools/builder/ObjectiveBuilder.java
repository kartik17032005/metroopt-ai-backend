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

    private LinearExprBuilder objective =
            LinearExpr.newBuilder();

    private final Map<ObjectiveWeight, Long> weightMap = new HashMap<>();

    public ObjectiveBuilder() {
        resetToDefaults();
    }

    public void reset() {
        this.objective = LinearExpr.newBuilder();
        resetToDefaults();
    }

    public void resetToDefaults() {
        weightMap.put(ObjectiveWeight.MILEAGE, OptimizationWeights.MILEAGE_WEIGHT);
        weightMap.put(ObjectiveWeight.BRANDING, OptimizationWeights.BRANDING_WEIGHT);
        weightMap.put(ObjectiveWeight.INSPECTION, OptimizationWeights.INSPECTION_PRIORITY_WEIGHT);
        weightMap.put(ObjectiveWeight.STANDBY, OptimizationWeights.STANDBY_PRIORITY_WEIGHT);
        weightMap.put(ObjectiveWeight.FLEET_UTILIZATION, OptimizationWeights.FLEET_UTILIZATION_WEIGHT);
    }

    public void configureStrategy(String strategy) {
        resetToDefaults();
        if (strategy == null || strategy.isBlank()) {
            return;
        }
        switch (strategy.trim().toUpperCase()) {
            case "MILEAGE_BALANCING" -> {
                weightMap.put(ObjectiveWeight.MILEAGE, 10L);
                weightMap.put(ObjectiveWeight.BRANDING, 500L);
                weightMap.put(ObjectiveWeight.STANDBY, 5L);
                weightMap.put(ObjectiveWeight.INSPECTION, 20L);
                weightMap.put(ObjectiveWeight.FLEET_UTILIZATION, 10L);
            }
            case "BRANDING_PRIORITY" -> {
                weightMap.put(ObjectiveWeight.MILEAGE, 1L);
                weightMap.put(ObjectiveWeight.BRANDING, 25000L);
                weightMap.put(ObjectiveWeight.STANDBY, 1L);
                weightMap.put(ObjectiveWeight.INSPECTION, 50L);
                weightMap.put(ObjectiveWeight.FLEET_UTILIZATION, 50L);
            }
            case "MAINTENANCE_FIRST" -> {
                weightMap.put(ObjectiveWeight.MILEAGE, 1L);
                weightMap.put(ObjectiveWeight.BRANDING, 1000L);
                weightMap.put(ObjectiveWeight.STANDBY, 5L);
                weightMap.put(ObjectiveWeight.INSPECTION, 200L);
                weightMap.put(ObjectiveWeight.FLEET_UTILIZATION, 50L);
            }
            case "ENERGY_EFFICIENT" -> {
                weightMap.put(ObjectiveWeight.MILEAGE, 2L);
                weightMap.put(ObjectiveWeight.BRANDING, 2000L);
                weightMap.put(ObjectiveWeight.STANDBY, 3L);
                weightMap.put(ObjectiveWeight.INSPECTION, 50L);
                weightMap.put(ObjectiveWeight.FLEET_UTILIZATION, 500L);
            }
            default -> {
                // BALANCED default
                weightMap.put(ObjectiveWeight.MILEAGE, 1L);
                weightMap.put(ObjectiveWeight.BRANDING, OptimizationWeights.BRANDING_BONUS);
                weightMap.put(ObjectiveWeight.STANDBY, OptimizationWeights.STANDBY_PRIORITY_WEIGHT);
                weightMap.put(ObjectiveWeight.INSPECTION, OptimizationWeights.INSPECTION_PRIORITY_WEIGHT);
                weightMap.put(ObjectiveWeight.FLEET_UTILIZATION, OptimizationWeights.FLEET_UTILIZATION_WEIGHT);
            }
        }
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