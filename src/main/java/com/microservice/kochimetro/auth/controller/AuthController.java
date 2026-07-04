package com.microservice.kochimetro.auth.controller;

import com.microservice.kochimetro.auth.dto.request.LoginRequest;
import com.microservice.kochimetro.auth.dto.request.RegisterRequest;
import com.microservice.kochimetro.auth.dto.response.LoginResponse;
import com.microservice.kochimetro.auth.dto.response.RegisterResponse;
import com.microservice.kochimetro.auth.service.AuthService;
import com.microservice.kochimetro.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Registering the user with the emailId: {}", registerRequest.getEmail());
        RegisterResponse savedUser = authService.register(registerRequest);

        log.info("User registered successfully");

        return
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ApiResponse.<RegisterResponse>builder()
                                .success(true)
                                .data(savedUser)
                                .message("User registered successfully")
                                .timestamp(Instant.now())
                                .build()
                        );
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Logging in the user with the emailId: {}", loginRequest.getEmail());

        LoginResponse loginUser = authService.login(loginRequest);

        log.info("User logged in successfully with email: {}", loginRequest.getEmail());

        return
                ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponse.<LoginResponse>builder()
                                .success(true)
                                .data(loginUser)
                                .message("User logged in successfully")
                                .timestamp(Instant.now())
                                .build()
                        );
    }


}
