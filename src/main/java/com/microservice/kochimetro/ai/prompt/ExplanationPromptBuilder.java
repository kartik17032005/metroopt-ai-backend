package com.microservice.kochimetro.ai.prompt;

import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.AllocationStatus;
import com.microservice.kochimetro.train.entity.Train;
import org.springframework.stereotype.Component;

//This class has one job: convert structured train data into a well-designed prompt.
@Component
public class ExplanationPromptBuilder {
    public String buildPrompt(
            TrainData train,
            AllocationStatus allocationStatus
    ){
        return """
                You are an AI assistant for Kochi Metro Rail's fleet optimization system.

                Your task is to explain why a train received its allocation.

                Train Details:
                ------------------------
                Train Number: %s
                Allocation: %s
                Fitness Certificate: %s
                Cleaning Completed: %s
                Critical Maintenance: %s
                Standby Eligible: %s
                Inspection Required: %s
                Mileage: %d km
                Depot: %s

                Instructions:
                - Explain in 2-3 professional sentences.
                - Only use the provided information.
                - Do NOT invent facts.
                - Keep the explanation concise.
                - Write for metro operations managers.
                """
                .formatted(
                        train.getTrainNumber(),
                        allocationStatus,
                        train.isFitnessValid(),
                        train.isCleaningCompleted(),
                        train.isCriticalMaintenance(),
                        train.isStandbyEligible(),
                        train.isInspectionDue(),
                        train.getMileage(),
                        train.getDepot()
                );
    }
}
