package com.microservice.kochimetro.depot.repository;

import com.microservice.kochimetro.depot.entity.DepotResource;
import com.microservice.kochimetro.train.entity.enums.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface DepotResourceRepository extends JpaRepository<DepotResource, UUID> {
    Optional<DepotResource> findByDepot(Depot depot);
}
