package com.microservice.kochimetro.branding.entity;

import com.microservice.kochimetro.branding.entity.enums.BrandingStatus;
import com.microservice.kochimetro.train.entity.Train;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "branding_contracts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BrandingContract {

    @Id
    @GeneratedValue
    private UUID id;

    @JoinColumn(name = "train_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Train train;

    @Column(nullable = false)
    private String advertiser;

    @Column(name = "campaign_name", nullable = false)
    private String campaignName;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BrandingStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;
}
