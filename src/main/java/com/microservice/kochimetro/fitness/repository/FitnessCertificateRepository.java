package com.microservice.kochimetro.fitness.repository;

import com.microservice.kochimetro.fitness.entity.FitnessCertificate;
import com.microservice.kochimetro.fitness.entity.enums.CertificateStatus;
import com.microservice.kochimetro.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FitnessCertificateRepository extends JpaRepository<FitnessCertificate, UUID> {
    Optional<FitnessCertificate> findByCertificateNumber(String certificateNumber);
    boolean existsByCertificateNumber(String certificateNumber);
    List<FitnessCertificate> findByTrainId(UUID trainId);
    List<FitnessCertificate> findByStatus(CertificateStatus status);
    Optional<FitnessCertificate> findTopByTrainOrderByIssuedAtDesc(Train train);
}
