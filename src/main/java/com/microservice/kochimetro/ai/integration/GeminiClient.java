package com.microservice.kochimetro.ai.integration;

import com.microservice.kochimetro.ai.config.GeminiProperties;
import com.microservice.kochimetro.ai.dto.Content;
import com.microservice.kochimetro.ai.dto.GeminiRequest;
import com.microservice.kochimetro.ai.dto.GeminiResponse;
import com.microservice.kochimetro.ai.dto.Part;
import lombok.RequiredArgsConstructor;
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
public class GeminiClient {
    private final RestClient restClient; // this is used for http
    private final GeminiProperties properties;


    public String generateContent(String prompt) {

        //converting prompt into
        //{"contents": [{parts:[{text}]}]}
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
                + properties.getModel()
                + ":generateContent?key="
                + properties.getApi().getKey();

        GeminiResponse response = restClient.post() //http post
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(GeminiResponse.class);


        if (response == null
                || response.getCandidates() == null
                || response.getCandidates().isEmpty()) {

            return "Unable to generate explanation.";

        }

        return response.getCandidates()
                .getFirst()
                .getContent()
                .getParts()
                .getFirst()
                .getText();

    }
}
