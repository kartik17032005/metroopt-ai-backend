package com.microservice.kochimetro.jobCard.entity;

import com.microservice.kochimetro.jobCard.entity.enums.JobPriority;
import com.microservice.kochimetro.jobCard.entity.enums.JobStatus;
import com.microservice.kochimetro.jobCard.entity.enums.MaintenanceType;
import com.microservice.kochimetro.train.entity.Train;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

/*
A job card is something where every problem of the train is mentioned
and who is gonna resolve it basically a collection of information about the train
(umm like telling in person with the help of call is omitted and a full document is
prepared know as job card for everyone)
 */
@Entity
@Table(name = "job_cards")
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobCard {

    @Id
    @GeneratedValue
    private UUID jobCardId;

    @JoinColumn(name = "train_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Train train;

    @Column(nullable = false)
    private String title; //like brake failed etc;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaintenanceType maintenanceType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobPriority priority;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    @Column(name = "assigned_engineer", nullable = false)
    private String assignedEngineer; //which engineer is assigned to particular problem

    @Column(name = "estimated_completion")
    private Instant estimatedCompletion;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

}
