package com.microservice.kochimetro.jobCard.repository;

import com.microservice.kochimetro.jobCard.entity.JobCard;
import com.microservice.kochimetro.jobCard.entity.enums.JobPriority;
import com.microservice.kochimetro.jobCard.entity.enums.JobStatus;
import com.microservice.kochimetro.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobCardRepository extends JpaRepository<JobCard, UUID> {
    List<JobCard> findByTrainId(UUID trainId);

    List<JobCard> findByJobStatus(JobStatus jobStatus);

    List<JobCard> findByPriority(JobPriority jobPriority);

    boolean existsByTrainAndJobStatusAndPriority(
            Train train,
            JobStatus jobStatus,
            JobPriority jobPriority
            );
}
