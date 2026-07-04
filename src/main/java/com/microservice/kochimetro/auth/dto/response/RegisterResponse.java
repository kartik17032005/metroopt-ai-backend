package com.microservice.kochimetro.auth.dto.response;

import com.microservice.kochimetro.auth.user.entity.enums.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
}
