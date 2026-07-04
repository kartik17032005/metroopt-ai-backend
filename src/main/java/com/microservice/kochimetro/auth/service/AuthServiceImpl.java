package com.microservice.kochimetro.auth.service;

import com.microservice.kochimetro.auth.dto.request.LoginRequest;
import com.microservice.kochimetro.auth.dto.request.RegisterRequest;
import com.microservice.kochimetro.auth.dto.response.LoginResponse;
import com.microservice.kochimetro.auth.dto.response.RegisterResponse;
import com.microservice.kochimetro.auth.mapper.AuthMapper;
import com.microservice.kochimetro.auth.user.entity.User;
import com.microservice.kochimetro.auth.user.repository.UserRepository;
import com.microservice.kochimetro.exception.BadRequestException;
import com.microservice.kochimetro.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, AuthMapper authMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authMapper = authMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse register(@Valid RegisterRequest registerRequest) {
        log.info("Registering user with email: {}", registerRequest.getEmail());

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = authMapper.toEntity(registerRequest);
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        user.setPassword(hashedPassword);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        log.info("User registered successfully with ID: {}", savedUser.getId());

        return authMapper.toRegisterResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Logging in user with email: {}", loginRequest.getEmail());

        User user = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        log.info("User logged in successfully with email: {}", user.getEmail());

        return authMapper.toLoginResponse(user);
    }
}
