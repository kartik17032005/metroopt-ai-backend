package com.microservice.kochimetro.fitness.entity;

import com.microservice.kochimetro.fitness.entity.enums.CertificateStatus;
import com.microservice.kochimetro.train.entity.Train;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "fitness_certificates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FitnessCertificate {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "certificate_number", nullable = false, unique = true)
    private String certificateNumber;

    @JoinColumn(name = "train_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Train train;

    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CertificateStatus status;

    @Column(name = "issued_by", nullable = false)
    private String issuedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;
}
