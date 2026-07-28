package com.microservice.kochimetro.ai.prompt;

import com.microservice.kochimetro.optimization.dto.response.SelectedTrainResponse;
import com.microservice.kochimetro.optimization.model.TrainData;
import org.springframework.stereotype.Component;

import java.util.List;

//This class has one job: convert structured train data into a well-designed prompt.
@Component
public class ExplanationPromptBuilder {
    public String buildPrompt(
            List<TrainData> trains,
            List<SelectedTrainResponse> selectedTrains
    ) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are a Senior Operations Controller at Kochi Metro Rail.
                
                You are reviewing the output of an optimization engine built using Google OR-Tools.
                
                The optimizer has already decided the allocation of every train.
                
                Your responsibility is NOT to change the allocation.
                
                Instead, explain WHY each decision makes operational sense.
                
                The optimization priorities are:
                
                1. Passenger safety (highest priority)
                2. Fitness certificate validity
                3. Cleaning completion
                4. Critical maintenance requirements
                5. Inspection requirements
                6. Standby readiness
                7. Mileage balancing across the fleet
                8. Efficient fleet utilization
                
                Guidelines:
                
                • Write exactly ONE professional sentence for each train.
                •You are NOT making the allocation.
                
                  The optimization engine has already made the decision.

                  Your job is ONLY to explain the optimization outcome using the provided data.

                  Never invent reasons.

                  If mileage balancing is not clearly indicated by the provided data, do not mention it.

                  If a trade-off is not explicitly supported by the provided information, do not speculate.

                  Base every explanation strictly on the supplied train attributes and allocation result.
                • Mention trade-offs whenever appropriate.
                • Never simply repeat every train attribute.
                • Focus on WHY this train was chosen instead of another.
                • Sound like a real metro operations manager.
                • Do NOT use markdown.
                • Do NOT use bullet points.
                • Do NOT use JSON.
                • Return only the explanations in the same order as the trains provided.
                
                ==============================
                TRAIN ALLOCATIONS
                ==============================
                
                """);

        for (SelectedTrainResponse selected : selectedTrains) {

            TrainData train = trains.stream()
                    .filter(t -> t.getTrainNumber().equals(selected.getTrainNumber()))
                    .findFirst()
                    .orElse(null);

            if (train == null) {
                continue;
            }

            prompt.append("""
                    Train Number : %s
                    Allocation : %s
                    Fitness Certificate : %s
                    Cleaning Completed : %s
                    Critical Maintenance : %s
                    Inspection Due : %s
                    Standby Eligible : %s
                    Mileage : %d km
                    Depot : %s
                    
                    """.formatted(
                    train.getTrainNumber(),
                    selected.getAllocationStatus(),
                    train.isFitnessValid() ? "Valid" : "Invalid",
                    train.isCleaningCompleted() ? "Completed" : "Pending",
                    train.isCriticalMaintenance() ? "Yes" : "No",
                    train.isInspectionDue() ? "Yes" : "No",
                    train.isStandbyEligible() ? "Yes" : "No",
                    train.getMileage(),
                    train.getDepot()
            ));
        }

        prompt.append("""
                Return the explanations exactly like this:
                
                1. KMRL-001 - ...
                2. KMRL-002 - ...
                3. KMRL-003 - ...
                
                Do not include introductions, conclusions, notes, or any extra text.
                """);

        return prompt.toString();
    }
}
