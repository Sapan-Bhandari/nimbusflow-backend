package com.sapan.auth.service;

import com.sapan.auth.dto.*;
import com.sapan.auth.entity.User;
import com.sapan.auth.repository.UserRepository;
import com.sapan.auth.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public void register(
            RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole("USER");

        userRepository.save(user);
    }

    public AuthResponse login(
            LoginRequest request) {

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow();

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid password"
            );
        }

        String token =
                jwtUtil.generateToken(
                        user.getEmail()
                );

        return new AuthResponse(token);
    }
}