package com.microservice.kochimetro.mileage.service;

import com.microservice.kochimetro.exception.ResourceNotFoundException;
import com.microservice.kochimetro.mileage.dto.response.MileagePredictionResponse;
import com.microservice.kochimetro.mileage.entity.MileageRecord;
import com.microservice.kochimetro.mileage.repository.MileageRecordRepository;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

//will apply machine learning algo
/*
    y = mx + c
    y -> mileage
    x -> number of days
    m -> daily mileage growth
    c -> starting / intercept value
 */
@Service
public class MileagePredictionServiceImpl implements MileagePredictionService {

    private final MileageRecordRepository mileageRecordRepository;
    private final TrainRepository trainRepository;

    public MileagePredictionServiceImpl(MileageRecordRepository mileageRecordRepository, TrainRepository trainRepository) {
        this.mileageRecordRepository = mileageRecordRepository;
        this.trainRepository = trainRepository;
    }

    @Override
    public MileagePredictionResponse predictMileage(UUID trainId) {
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainId));

        List<MileageRecord> records = mileageRecordRepository.findByTrainIdOrderByRecordedAtAsc(trainId);

        if (records.size() < 3) {
            throw new IllegalStateException("Not enough mileage records for train with id: " + trainId);
        }

        double[] x = new double[records.size()];
        double[] y = new double[records.size()];

        long firstTimestamp = records.get(0).getRecordedAt().toEpochMilli();

        for (int i = 0; i < records.size(); i++) {
            MileageRecord mileageRecord = records.get(i);

            long timestamp = mileageRecord.getRecordedAt().toEpochMilli();

            double days = (timestamp - firstTimestamp) / (1000.0 * 60 * 60 * 24);

            x[i] = days; // instead of june 1, june 2, it is 0, 1, 2, 3
            y[i] = mileageRecord.getMileage();
        }

        //calculate the regression line
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumX2 = 0;

        int n = records.size();

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        //predict 7 days into the future
        double currentMileage = train.getMileage();
        double lastDay = x[n - 1];
        double futureDay = lastDay + 7;
        double predictedMileage = slope * futureDay + intercept;

        int predictedMileageIn7Days = (int) Math.round(predictedMileage);

        //calculate prediction date
        LocalDate predictionDate =
                records.get(records.size() - 1)
                        .getRecordedAt()
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()
                        .plusDays(7);

        String riskLevel;

        if (slope >= 90) {
            riskLevel = "HIGH";
        } else if (slope >= 70) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        return MileagePredictionResponse.builder()
                .trainNumber(train.getTrainNumber())
                .currentMileage(train.getMileage())
                .dailyMileageRate(
                        Math.round(slope * 100.0) / 100.0
                )
                .predictedMileageIn7Days(
                        predictedMileageIn7Days
                )
                .predictionDate(predictionDate)
                .riskLevel(riskLevel)
                .recommendation(
                        "Monitor mileage usage and review upcoming maintenance requirements."
                )
                .build();
    }
}
