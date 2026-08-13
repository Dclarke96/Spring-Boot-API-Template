package com.dylanclarke.springbootapitemplate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dylanclarke.springbootapitemplate.api.ApiResponse;
import com.dylanclarke.springbootapitemplate.dto.AuthRequest;
import com.dylanclarke.springbootapitemplate.dto.ErrorResponse;
import com.dylanclarke.springbootapitemplate.dto.RegisterRequest;
import com.dylanclarke.springbootapitemplate.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
name = "Authentication",
description = "Endpoints for user registration and authentication."
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final AuthenticationService authenticationService;

        public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        }

        @Operation(
                summary = "Register a new user",
                description = "Creates a new user account."
        )
        @io.swagger.v3.oas.annotations.responses.ApiResponses({

                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "201",
                        description = "User registered successfully"
                ),

                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Invalid registration request",
                        content = @Content(
                                schema = @Schema(
                                        implementation = ErrorResponse.class
                                )
                        )
                ),

                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "409",
                        description = "Username already exists",
                        content = @Content(
                                schema = @Schema(
                                        implementation = ErrorResponse.class
                                )
                        )
                )
        })
        @PostMapping("/register")
        public ResponseEntity<ApiResponse<String>> register(
                @Valid @RequestBody RegisterRequest request) {

        ApiResponse<String> response =
                authenticationService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
        }

        @Operation(
                summary = "Authenticate a user",
                description = "Validates credentials and returns a JWT token used to access protected endpoints."
        )
        @io.swagger.v3.oas.annotations.responses.ApiResponses({

                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Authentication successful"
                ),

                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Invalid login request",
                        content = @Content(
                                schema = @Schema(
                                        implementation = ErrorResponse.class
                                )
                        )
                ),

                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "401",
                        description = "Invalid username or password",
                        content = @Content(
                                schema = @Schema(
                                        implementation = ErrorResponse.class
                                )
                        )
                )
        })
        @PostMapping("/login")
        public ResponseEntity<ApiResponse<String>> login(
                @Valid @RequestBody AuthRequest request) {

        ApiResponse<String> response =
                authenticationService.login(request);

        if (!response.isSuccess()) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(response);
        }

        return ResponseEntity.ok(response);
        }

}
