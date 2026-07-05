package com.microservice.kochimetro.optimization.orTools.config;


public final class OptimizationWeights {

    private OptimizationWeights() {}

    public static final long MILEAGE_WEIGHT = 1L;
    public static final long BRANDING_WEIGHT = 1L;
    public static final long BRANDING_BONUS = 1L;

    //just a starting point
    public static final long STANDBY_PRIORITY_WEIGHT = 1L;
    public static final long INSPECTION_PRIORITY_WEIGHT = 1L;
    public static final long FLEET_UTILIZATION_WEIGHT = 1000L;

}
