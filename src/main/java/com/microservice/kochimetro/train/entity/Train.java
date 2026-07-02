package com.microservice.kochimetro.train.entity;

import com.microservice.kochimetro.train.entity.enums.BrandingStatus;
import com.microservice.kochimetro.train.entity.enums.TrainStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Train {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String trainNumber;

    @Column(nullable = false)
    private String model;

    private Integer mileage;

    @Enumerated(EnumType.STRING) //without this it will store 0, 1, 2 instead of available revenue standby etc
    private TrainStatus status;

    private String depot;

    @Enumerated(EnumType.STRING)
    @Column(name = "branding_status")
    private BrandingStatus brandingStatus;

    @Column(name = "created_At")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updatedAt")
    @LastModifiedDate
    private Instant updatedAt;
}
