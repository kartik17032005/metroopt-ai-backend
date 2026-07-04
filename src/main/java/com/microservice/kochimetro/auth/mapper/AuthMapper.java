package com.microservice.kochimetro.auth.mapper;

import com.microservice.kochimetro.auth.dto.request.RegisterRequest;
import com.microservice.kochimetro.auth.dto.response.LoginResponse;
import com.microservice.kochimetro.auth.dto.response.RegisterResponse;
import com.microservice.kochimetro.auth.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User toEntity(RegisterRequest registerRequest){
        return User.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(registerRequest.getRole())
                .build();
    }

    public RegisterResponse toRegisterResponse(User user){
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    public LoginResponse toLoginResponse(User user){
        return LoginResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}
