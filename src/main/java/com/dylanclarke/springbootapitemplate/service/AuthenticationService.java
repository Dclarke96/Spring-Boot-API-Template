package com.dylanclarke.springbootapitemplate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dylanclarke.springbootapitemplate.api.ApiResponse;
import com.dylanclarke.springbootapitemplate.dto.AuthRequest;
import com.dylanclarke.springbootapitemplate.dto.RegisterRequest;
import com.dylanclarke.springbootapitemplate.exception.AuthenticationException;
import com.dylanclarke.springbootapitemplate.exception.DuplicateResourceException;
import com.dylanclarke.springbootapitemplate.model.Role;
import com.dylanclarke.springbootapitemplate.model.User;
import com.dylanclarke.springbootapitemplate.repository.UserRepository;
import com.dylanclarke.springbootapitemplate.security.JwtService;

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