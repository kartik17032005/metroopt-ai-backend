package com.microservice.kochimetro.auth.service;

import com.microservice.kochimetro.auth.dto.request.LoginRequest;
import com.microservice.kochimetro.auth.dto.request.RegisterRequest;
import com.microservice.kochimetro.auth.dto.response.LoginResponse;
import com.microservice.kochimetro.auth.dto.response.RegisterResponse;
import org.springframework.stereotype.Service;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
