package com.microservice.kochimetro.branding.repository;

import com.microservice.kochimetro.branding.entity.BrandingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BrandingContractRepository extends JpaRepository<BrandingContract, UUID> {
    List<BrandingContract> findByTrainId(UUID trainId);
}
