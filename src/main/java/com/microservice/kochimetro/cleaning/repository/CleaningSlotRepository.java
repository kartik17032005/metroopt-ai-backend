package com.microservice.kochimetro.cleaning.repository;

import com.microservice.kochimetro.cleaning.entity.CleaningSlot;
import com.microservice.kochimetro.cleaning.entity.enums.CleaningStatus;
import com.microservice.kochimetro.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CleaningSlotRepository extends JpaRepository<CleaningSlot, UUID> {
    List<CleaningSlot> findByTrainId(UUID trainId);
    List<CleaningSlot> findByStatus(CleaningStatus status);
    Optional<CleaningSlot> findTopByTrainOrderByScheduledTimeDesc(Train train);
}
