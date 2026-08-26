package com.microservice.kochimetro.mileage.service;

// this is gonna predict the mileage for eg:
/*
    Current mileage = 7620 km
    Inspection threshold = 8000 km
    Days - 6 days required to reach threshold , so we will show this to the user

    KMR-014
    Current mileage:       7,620 km
    Threshold:             8,000 km
    Estimated daily usage:    63 km/day
    Predicted crossing:    6 days
    Predicted date:        Aug 21

Risk: HIGH
*/

import com.microservice.kochimetro.mileage.dto.response.MileagePredictionResponse;

import java.util.UUID;

/*
    Train ID
       ↓
    Get MileageRecord history
       ↓
    Check if enough data exists
       ↓
    Calculate mileage trend
       ↓
    Linear Regression
       ↓
    Predict 8,000 km crossing
       ↓
    Return predictionTrain ID
 */
public interface MileagePredictionService {
    MileagePredictionResponse predictMileage(UUID trainId);
}
