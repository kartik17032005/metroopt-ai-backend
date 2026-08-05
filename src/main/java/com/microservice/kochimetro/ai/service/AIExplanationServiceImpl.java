package com.microservice.kochimetro.ai.service;

import com.microservice.kochimetro.ai.integration.GeminiClient;
import com.microservice.kochimetro.ai.prompt.ExplanationPromptBuilder;
import com.microservice.kochimetro.optimization.dto.response.SelectedTrainResponse;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.AllocationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIExplanationServiceImpl implements AIExplanationService {

    private final ExplanationPromptBuilder promptBuilder;
    private final GeminiClient geminiClient;

    @Override
    public void enrichWithAIExplanations(
            List<TrainData> trains,
            List<SelectedTrainResponse> selectedTrains
    ) {
        if (selectedTrains == null || selectedTrains.isEmpty()) {
            return;
        }

        try {
            String prompt = promptBuilder.buildPrompt(
                    trains,
                    selectedTrains
            );

            String response = geminiClient.generateContent(prompt);

            if (response == null || response.isBlank()) {
                applyFallbackExplanations(selectedTrains);
                return;
            }

            String[] lines = response.split("\\r?\\n");

            int index = 0;

            for (SelectedTrainResponse selectedTrain : selectedTrains) {

                while (index < lines.length && lines[index].isBlank()) {
                    index++;
                }

                if (index >= lines.length) {
                    break;
                }

                String explanation = lines[index]
                        .replaceFirst("^\\d+\\.\\s*", "")
                        .trim();

                selectedTrain.setExplanation(explanation);

                index++;
            }

        } catch (Throwable throwable) {

            log.error("Failed to generate AI explanations, falling back to standard explanations", throwable);
            applyFallbackExplanations(selectedTrains);
        }
    }

    private void applyFallbackExplanations(List<SelectedTrainResponse> selectedTrains) {
        for (SelectedTrainResponse train : selectedTrains) {
            if (train.getExplanation() == null || train.getExplanation().isBlank()) {
                String status = train.getAllocationStatus() != null ? train.getAllocationStatus().name() : "DEPOT";
                switch (status) {
                    case "OPERATING" -> train.setExplanation("Selected for revenue operation based on optimal low mileage and valid fitness certificate.");
                    case "STANDBY" -> train.setExplanation("Allocated as standby reserve based on depot readiness and safety protocols.");
                    case "INSPECTION" -> train.setExplanation("Scheduled for routine maintenance inspection based on mileage accumulation.");
                    default -> train.setExplanation("Retained at depot for stabling and scheduled maintenance.");
                }
            }
        }
    }
}
