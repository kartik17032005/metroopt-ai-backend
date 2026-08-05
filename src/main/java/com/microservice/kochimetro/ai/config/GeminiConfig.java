package com.microservice.kochimetro.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GeminiConfig {

    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(5000);

        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
        mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        mediaTypes.add(MediaType.ALL);
        jacksonConverter.setSupportedMediaTypes(mediaTypes);

        return RestClient.builder()
                .requestFactory(requestFactory)
                .messageConverters(converters -> converters.add(0, jacksonConverter))
                .build();
    }
}
