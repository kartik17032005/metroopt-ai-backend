package com.microservice.kochimetro.auth.dto.response;

import com.microservice.kochimetro.auth.user.entity.enums.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private UUID userId;
    private String fullName;
    private String email;
    private Role role;
    private Long expiresIn;
}
