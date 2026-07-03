package com.microservice.kochimetro.mileage.repository;

import com.microservice.kochimetro.mileage.entity.MileageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MileageRecordRepository extends JpaRepository<MileageRecord, UUID> {
    List<MileageRecord> findByTrainId(UUID trainId);
}
