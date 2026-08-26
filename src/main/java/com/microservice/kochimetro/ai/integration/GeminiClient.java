package com.microservice.kochimetro.ai.integration;

import com.microservice.kochimetro.ai.config.GeminiProperties;
import com.microservice.kochimetro.ai.dto.Content;
import com.microservice.kochimetro.ai.dto.GeminiRequest;
import com.microservice.kochimetro.ai.dto.GeminiResponse;
import com.microservice.kochimetro.ai.dto.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/*
    -> Receive the prompt
    -> sent it to the gemini
    -> return the response
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GeminiClient {
    private final RestClient restClient; // this is used for http
    private final GeminiProperties properties;


    public String generateContent(String prompt) {
        if (properties == null || properties.getApi() == null || properties.getApi().getKey() == null || properties.getApi().getKey().isBlank()) {
            log.info("Gemini API key is not configured; using fallback explanations.");
            return null;
        }

        String model = properties.getModel();
        if (model == null || model.isBlank()) {
            model = "gemini-2.0-flash";
        }

        String result = callModel(prompt, model);
        if (result == null && !model.equals("gemini-1.5-flash")) {
            log.info("Attempting Gemini fallback with gemini-1.5-flash");
            result = callModel(prompt, "gemini-1.5-flash");
        }
        return result;
    }

    private String callModel(String prompt, String model) {
        try {
            GeminiRequest request = new GeminiRequest(
                    List.of(
                            new Content(
                                    List.of(
                                            new Part(prompt)
                                    )
                            )
                    )
            );

            String url = properties.getApi().getUrl()
                    + "/"
                    + model
                    + ":generateContent?key="
                    + properties.getApi().getKey();

            GeminiResponse response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(GeminiResponse.class);

            if (response == null
                    || response.getCandidates() == null
                    || response.getCandidates().isEmpty()
                    || response.getCandidates().getFirst().getContent() == null
                    || response.getCandidates().getFirst().getContent().getParts() == null
                    || response.getCandidates().getFirst().getContent().getParts().isEmpty()) {
                return null;
            }

            return response.getCandidates()
                    .getFirst()
                    .getContent()
                    .getParts()
                    .getFirst()
                    .getText();
        } catch (Exception ex) {
            log.warn("Gemini API call failed for model {}: {}", model, ex.getMessage());
            return null;
        }
    }

    }
}
