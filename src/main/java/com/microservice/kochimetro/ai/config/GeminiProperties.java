package com.microservice.kochimetro.ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gemini") //take everything starting with "gemini" from application.yml
public class GeminiProperties {

    private Api api = new Api();
    private String model;

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Getter
    @Setter
    public static class Api{
        private String key;
        private String url;
    }
}
