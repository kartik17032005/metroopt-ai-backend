package com.microservice.kochimetro.cleaning.entity;

import com.microservice.kochimetro.cleaning.entity.enums.CleaningStatus;
import com.microservice.kochimetro.cleaning.entity.enums.CleaningType;
import com.microservice.kochimetro.train.entity.Train;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cleaning_slots")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CleaningSlot {

    @Id
    @GeneratedValue
    private UUID id;

    @JoinColumn(name = "train_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Train train;

    @Column(name = "cleaning_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CleaningType cleaningType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CleaningStatus status;

    @Column(name = "scheduled_time", nullable = false)
    private Instant scheduledTime;

    @Column(name = "completed_time")
    private Instant completedTime;

    @Column(name = "assigned_staff", nullable = false)
    private String assignedStaff;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;
}
