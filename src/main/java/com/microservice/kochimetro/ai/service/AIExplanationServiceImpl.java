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

            if (response != null && !response.isBlank()) {
                parseAndApplyGeminiResponse(response, selectedTrains);
            }

        } catch (Throwable throwable) {
            log.error("Failed to generate AI explanations, falling back to contextual explanations", throwable);
        }

        // Fill any remaining/unfilled train explanations with contextual explanations
        applyFallbackExplanations(trains, selectedTrains);
    }

    private void parseAndApplyGeminiResponse(String response, List<SelectedTrainResponse> selectedTrains) {
        String[] lines = response.split("\\r?\\n");
        Map<String, String> parsedExplanations = new java.util.HashMap<>();

        // Pattern to match lines like: "1. TS-01 - ...", "TS-01: ...", "**TS-01**: ...", "TS-01 - ..."
        java.util.regex.Pattern trainLinePattern = java.util.regex.Pattern.compile(
                "(?i)^\\s*(?:\\d+\\.\\s*|-|\\*)?\\s*(?:\\*\\*)?(TS-\\d+|[A-Z0-9_-]+)(?:\\*\\*)?\\s*[:\\-–—]\\s*(.+)$"
        );

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isBlank()) continue;

            java.util.regex.Matcher matcher = trainLinePattern.matcher(trimmed);
            if (matcher.find()) {
                String trainNum = matcher.group(1).trim().toUpperCase();
                String explanation = matcher.group(2).trim();
                parsedExplanations.put(trainNum, explanation);
            }
        }

        // Assign parsed explanations
        int lineIndex = 0;
        for (SelectedTrainResponse selectedTrain : selectedTrains) {
            String trainNumber = selectedTrain.getTrainNumber();
            if (trainNumber != null && parsedExplanations.containsKey(trainNumber.toUpperCase())) {
                selectedTrain.setExplanation(parsedExplanations.get(trainNumber.toUpperCase()));
            } else {
                // Fallback to non-blank line matching if regex didn't catch specific format
                while (lineIndex < lines.length && lines[lineIndex].trim().isBlank()) {
                    lineIndex++;
                }
                if (lineIndex < lines.length) {
                    String rawLine = lines[lineIndex].trim()
                            .replaceFirst("^\\d+\\.\\s*", "")
                            .replaceFirst("^(?i)" + (trainNumber != null ? java.util.regex.Pattern.quote(trainNumber) : "") + "\\s*[:\\-–—]\\s*", "")
                            .trim();
                    if (!rawLine.isBlank() && !rawLine.startsWith("Here are") && !rawLine.startsWith("Below are")) {
                        selectedTrain.setExplanation(rawLine);
                    }
                    lineIndex++;
                }
            }
        }
    }

    private void applyFallbackExplanations(List<TrainData> trains, List<SelectedTrainResponse> selectedTrains) {
        Map<String, TrainData> trainDataMap = trains != null
                ? trains.stream().collect(java.util.stream.Collectors.toMap(TrainData::getTrainNumber, java.util.function.Function.identity(), (a, b) -> a))
                : Map.of();

        for (SelectedTrainResponse selected : selectedTrains) {
            if (selected.getExplanation() != null && !selected.getExplanation().isBlank()) {
                continue;
            }

            TrainData data = trainDataMap.get(selected.getTrainNumber());
            String depotName = (data != null && data.getDepot() != null) ? data.getDepot().name() : "Depot";
            int mileage = data != null ? data.getMileage() : 0;
            String status = selected.getAllocationStatus() != null ? selected.getAllocationStatus().name() : "DEPOT";

            String explanation;
            switch (status) {
                case "OPERATING" -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Allocated to revenue service at ").append(depotName)
                      .append(" based on balanced mileage (").append(String.format("%,d", mileage)).append(" km)")
                      .append(" and verified safety fitness.");
                    if (data != null && data.isBranded()) {
                        sb.append(" Prioritized to fulfill active commercial branding obligations.");
                    }
                    explanation = sb.toString();
                }
                case "STANDBY" -> {
                    explanation = "Designated as standby reserve at " + depotName + " depot with optimal readiness and active safety clearances.";
                }
                case "INSPECTION" -> {
                    if (data != null && data.isCriticalMaintenance()) {
                        explanation = "Routed to " + depotName + " inspection bay due to logged critical maintenance job card.";
                    } else if (data != null && !data.isFitnessValid()) {
                        explanation = "Scheduled for urgent inspection at " + depotName + " bay for fitness certificate recertification.";
                    } else if (data != null && data.isInspectionDue()) {
                        explanation = "Assigned to maintenance inspection at " + depotName + " bay due to cumulative mileage (" + String.format("%,d", mileage) + " km) exceeding threshold.";
                    } else {
                        explanation = "Scheduled for routine preventive inspection and diagnostics at " + depotName + " bay.";
                    }
                }
                default -> {
                    if (data != null && !data.isCleaningCompleted()) {
                        explanation = "Held at " + depotName + " depot stabling yard awaiting completion of scheduled cleaning.";
                    } else if (data != null && !data.isFitnessValid()) {
                        explanation = "Held at " + depotName + " depot pending safety certificate renewal.";
                    } else {
                        explanation = "Retained at " + depotName + " depot stabling tracks in secondary reserve.";
                    }
                }
            }

            selected.setExplanation(explanation);
        }
    }
}
