package com.microservice.kochimetro.optimization.service;

import com.microservice.kochimetro.branding.entity.BrandingContract;
import com.microservice.kochimetro.branding.entity.enums.BrandingStatus;
import com.microservice.kochimetro.branding.repository.BrandingContractRepository;
import com.microservice.kochimetro.cleaning.entity.CleaningSlot;
import com.microservice.kochimetro.cleaning.entity.enums.CleaningStatus;
import com.microservice.kochimetro.cleaning.repository.CleaningSlotRepository;
import com.microservice.kochimetro.depot.entity.DepotResource;
import com.microservice.kochimetro.depot.repository.DepotResourceRepository;
import com.microservice.kochimetro.fitness.entity.FitnessCertificate;
import com.microservice.kochimetro.fitness.entity.enums.CertificateStatus;
import com.microservice.kochimetro.fitness.repository.FitnessCertificateRepository;
import com.microservice.kochimetro.jobCard.entity.enums.JobPriority;
import com.microservice.kochimetro.jobCard.entity.enums.JobStatus;
import com.microservice.kochimetro.jobCard.repository.JobCardRepository;
import com.microservice.kochimetro.mileage.entity.MileageRecord;
import com.microservice.kochimetro.mileage.repository.MileageRecordRepository;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Collect data from all modules and convert it into TrainData objects
 * which are passed to the OR-Tools solver.
 */

@Service
@RequiredArgsConstructor
public class TrainDataBuilder {

    private final TrainRepository trainRepository;
    private final FitnessCertificateRepository fitnessCertificateRepository;
    private final CleaningSlotRepository cleaningSlotRepository;
    private final JobCardRepository jobCardRepository;
    private final MileageRecordRepository mileageRecordRepository;
    private final BrandingContractRepository brandingContractRepository;

    public List<TrainData> buildContext() {

        List<Train> trains = trainRepository.findAll();
        List<TrainData> trainDataList = new ArrayList<>();

        for (Train train : trains) {

            Optional<FitnessCertificate> fitnessCertificate =
                    fitnessCertificateRepository
                            .findTopByTrainOrderByIssuedAtDesc(train);

            Optional<CleaningSlot> cleaningSlot =
                    cleaningSlotRepository
                            .findTopByTrainOrderByScheduledTimeDesc(train);

            boolean hasCriticalMaintenance =
                    jobCardRepository.existsByTrainAndJobStatusAndPriority(
                            train,
                            JobStatus.OPEN,
                            JobPriority.CRITICAL
                    );


            Optional<MileageRecord> mileageRecord =
                    mileageRecordRepository
                            .findTopByTrainOrderByRecordedAtDesc(train);

            Optional<BrandingContract> brandingContract =
                    brandingContractRepository.findByTrainAndStatus(
                            train,
                            BrandingStatus.ACTIVE
                    );

            boolean fitnessValid = fitnessCertificate
                    .map(fc -> fc.getStatus() == CertificateStatus.VALID)
                    .orElse(false);

            boolean cleaningCompleted = cleaningSlot
                    .map(cs -> cs.getStatus() == CleaningStatus.COMPLETED)
                    .orElse(false);

            boolean brandingActive = brandingContract.isPresent();

            boolean standbyEligible =
                    fitnessValid
                            && cleaningCompleted
                            && !hasCriticalMaintenance;

            int mileage = mileageRecord
                    .map(MileageRecord::getMileage)
                    .orElse(0);

            boolean inspectionDue = mileage >= 8000;

            int inspectionPriority = 0;

            if (hasCriticalMaintenance) {
                inspectionPriority = 100;
            }
            else if (inspectionDue) {
                inspectionPriority = 80;
            }
            else if (mileage >= 7000) {
                inspectionPriority = 50;
            }
            else {
                inspectionPriority = 10;
            }

            TrainData trainData = TrainData.builder()
                    .trainId(train.getId())
                    .trainNumber(train.getTrainNumber())
                    .fitnessValid(fitnessValid)
                    .cleaningCompleted(cleaningCompleted)
                    .criticalMaintenance(hasCriticalMaintenance)
                    .mileage(mileage)
                    .depot(train.getDepot())
                    .inspectionDue(inspectionDue)
                    .inspectionPriority(inspectionPriority)
                    .brandingActive(brandingActive)
                    .standbyEligible(standbyEligible)
                    .build();

            trainDataList.add(trainData);
        }

        return trainDataList;
    }
}