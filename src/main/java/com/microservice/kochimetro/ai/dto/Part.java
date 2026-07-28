package com.microservice.kochimetro.ai.dto;
/*
    {
  "contents": [
    {
      "parts": [
        {
          "text": "Explain why KMRL-006 was selected..."
        }
      ]
    }
  ]
}
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Part {
    private String text;
}
