package com.microservice.kochimetro;

import com.microservice.kochimetro.jobCard.entity.JobCard;
import com.microservice.kochimetro.jobCard.entity.enums.JobPriority;
import com.microservice.kochimetro.jobCard.entity.enums.JobStatus;
import com.microservice.kochimetro.jobCard.entity.enums.MaintenanceType;
import com.microservice.kochimetro.jobCard.repository.JobCardRepository;
import com.microservice.kochimetro.train.entity.Train;
import com.microservice.kochimetro.train.entity.enums.TrainStatus;
import com.microservice.kochimetro.train.repository.TrainRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class KochiMetroApplicationTests {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private JobCardRepository jobCardRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testSaveJobCard() {
        Train train = Train.builder()
                .trainNumber("T-" + UUID.randomUUID().toString().substring(0, 8))
                .model("Model-A")
                .mileage(100)
                .status(TrainStatus.STANDBY)
                .build();
        train = trainRepository.save(train);
        assertNotNull(train.getId());

        JobCard jobCard = JobCard.builder()
                .title("Brake Issue")
                .description("Brakes are slipping")
                .maintenanceType(MaintenanceType.BRAKING)
                .priority(JobPriority.HIGH)
                .jobStatus(JobStatus.OPEN)
                .assignedEngineer("John Doe")
                .estimatedCompletion(Instant.now().plusSeconds(3600))
                .train(train)
                .build();

        JobCard saved = jobCardRepository.save(jobCard);
        assertNotNull(saved.getJobCardId());
    }

}


