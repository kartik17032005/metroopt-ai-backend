package com.microservice.kochimetro.depot.entity;

import com.microservice.kochimetro.train.entity.enums.Depot;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "depot_resources", uniqueConstraints = {@UniqueConstraint(columnNames = "depot")})
public class DepotResource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Depot depot;

    @Column(nullable = false)
    private Integer inspectionBayCapacity;

    @Column(nullable = false)
    private Integer standbyTrackCapacity;

    @Column(nullable = false)
    private Integer stablingTrackCapacity;

    @Column(nullable = false)
    private Integer washingLineCapacity;


}
