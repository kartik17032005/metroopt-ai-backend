package com.microservice.kochimetro.mileage.entity;

import com.microservice.kochimetro.train.entity.Train;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "mileage_records")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MileageRecord {

    @Id
    @GeneratedValue
    private UUID id;

    @JoinColumn(name = "train_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Train train;

    @Column(nullable = false)
    private Integer mileage;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @Column(name = "recorded_by", nullable = false)
    private String recordedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;
}
