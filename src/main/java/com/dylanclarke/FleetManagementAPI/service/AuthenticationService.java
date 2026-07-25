package com.dylanclarke.FleetManagementAPI.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dylanclarke.FleetManagementAPI.api.ApiResponse;
import com.dylanclarke.FleetManagementAPI.dto.AuthRequest;
import com.dylanclarke.FleetManagementAPI.dto.RegisterRequest;
import com.dylanclarke.FleetManagementAPI.exception.AuthenticationException;
import com.dylanclarke.FleetManagementAPI.exception.DuplicateResourceException;
import com.dylanclarke.FleetManagementAPI.model.Role;
import com.dylanclarke.FleetManagementAPI.model.User;
import com.dylanclarke.FleetManagementAPI.repository.UserRepository;
import com.dylanclarke.FleetManagementAPI.security.JwtService;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public ApiResponse<String> register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {

            log.warn(
                    "Registration failed: username already exists username={}",
                    request.getUsername()
            );

            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        log.info(
                "User registered: userId={}, role={}",
                user.getId(),
                user.getRole()
        );

        return ApiResponse.success(
                null,
                "User registered successfully"
        );
    }

    public ApiResponse<String> login(AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn(
                            "Login failed: unknown username={}",
                            request.getUsername()
                    );
                    return new AuthenticationException("Invalid credentials");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            log.warn(
                    "Login failed: invalid password userId={}",
                    user.getId()
            );

            throw new AuthenticationException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        log.info(
                "Login successful: userId={}",
                user.getId()
        );

        return ApiResponse.success(
                token,
                "Login successful"
        );
    }
}