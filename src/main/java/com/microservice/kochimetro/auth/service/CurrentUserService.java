package com.microservice.kochimetro.auth.service;

import com.microservice.kochimetro.auth.user.entity.User;
import com.microservice.kochimetro.auth.user.repository.UserRepository;
import com.microservice.kochimetro.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {
    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email));
    }

    public UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public String getCurrentUserEmail(){
        return getCurrentUser().getEmail();
    }

    public String getCurrentUserFullName(){
        return getCurrentUser().getFullName();
    }

    public boolean isCurrentUserAdmin(){
        return getCurrentUser().getRole().name().equals("ADMIN");
    }
}
