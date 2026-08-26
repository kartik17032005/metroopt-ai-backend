package com.microservice.kochimetro.optimization.orTools.config;


public final class OptimizationWeights {

    private OptimizationWeights() {}

    public static final long MILEAGE_WEIGHT = 1L;
    public static final long BRANDING_BONUS = 3000L;
    public static final long BRANDING_WEIGHT = 1L;
    public static final long STANDBY_PRIORITY_WEIGHT = 2L;
    public static final long INSPECTION_PRIORITY_WEIGHT = 50L;
    public static final long FLEET_UTILIZATION_WEIGHT = 100L;
}
