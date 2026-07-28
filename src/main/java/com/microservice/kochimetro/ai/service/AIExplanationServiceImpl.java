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

        try {

            String prompt = promptBuilder.buildPrompt(
                    trains,
                    selectedTrains
            );

            String response = geminiClient.generateContent(prompt);

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

        } catch (Exception exception) {

            log.error("Failed to generate AI explanations", exception);

            for (SelectedTrainResponse selectedTrain : selectedTrains) {
                selectedTrain.setExplanation(
                        "AI explanation is currently unavailable."
                );
            }
        }
    }
}
