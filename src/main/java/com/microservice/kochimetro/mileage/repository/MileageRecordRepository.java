package com.microservice.kochimetro.mileage.repository;

import com.microservice.kochimetro.mileage.entity.MileageRecord;
import com.microservice.kochimetro.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MileageRecordRepository extends JpaRepository<MileageRecord, UUID> {
    List<MileageRecord> findByTrainId(UUID trainId);

    Optional<MileageRecord> findTopByTrainOrderByRecordedAtDesc(Train train);
}
