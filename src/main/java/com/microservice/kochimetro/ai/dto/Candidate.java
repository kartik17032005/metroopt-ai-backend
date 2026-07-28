package com.microservice.kochimetro.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//gemini will response this content
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    private Content content;
}
