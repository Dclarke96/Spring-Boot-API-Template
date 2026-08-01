package com.dylanclarke.springbootapitemplate.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dylanclarke.springbootapitemplate.api.ApiResponse;
import com.dylanclarke.springbootapitemplate.dto.AuthRequest;
import com.dylanclarke.springbootapitemplate.dto.RegisterRequest;
import com.dylanclarke.springbootapitemplate.exception.AuthenticationException;
import com.dylanclarke.springbootapitemplate.exception.DuplicateResourceException;
import com.dylanclarke.springbootapitemplate.model.Role;
import com.dylanclarke.springbootapitemplate.model.User;
import com.dylanclarke.springbootapitemplate.repository.UserRepository;
import com.dylanclarke.springbootapitemplate.security.JwtService;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private AuthenticationService authenticationService;


    @BeforeEach
    void setUp() {

        authenticationService =
                new AuthenticationService(
                        userRepository,
                        passwordEncoder,
                        jwtService
                );
    }


    // =========================================================
    // REGISTER
    // =========================================================

    @Test
    void register_shouldCreateUserSuccessfully() {

        // Arrange
        RegisterRequest request =
                createValidRegisterRequest();

        when(userRepository.findByUsername(
                request.getUsername()
        )).thenReturn(Optional.empty());

        when(passwordEncoder.encode(
                request.getPassword()
        )).thenReturn("encodedPassword");

        ArgumentCaptor<User> captor =
                ArgumentCaptor.forClass(User.class);

        // Act
        ApiResponse<String> result =
                authenticationService.register(request);

        // Assert
        assertNotNull(result);

        verify(userRepository)
                .save(captor.capture());

        User savedUser =
                captor.getValue();

        assertEquals(
                "testuser",
                savedUser.getUsername()
        );

        assertEquals(
                "test@example.com",
                savedUser.getEmail()
        );

        assertEquals(
                "encodedPassword",
                savedUser.getPassword()
        );

        assertEquals(
                Role.ADMIN,
                savedUser.getRole()
        );

        verify(userRepository)
                .findByUsername("testuser");

        verify(passwordEncoder)
                .encode("password123");
    }


    @Test
    void register_shouldThrowWhenUsernameAlreadyExists() {

        // Arrange
        RegisterRequest request =
                createValidRegisterRequest();

        User existingUser =
                createUser();

        when(userRepository.findByUsername(
                request.getUsername()
        )).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(
                DuplicateResourceException.class,
                () -> authenticationService.register(request)
        );

        verify(userRepository)
                .findByUsername("testuser");

        verify(userRepository, never())
                .save(any());

        verify(passwordEncoder, never())
                .encode(anyString());
    }


    // =========================================================
    // LOGIN
    // =========================================================

    @Test
    void login_shouldAuthenticateUserSuccessfully() {

        // Arrange
        AuthRequest request =
                createValidAuthRequest();

        User user =
                createUser();

        when(userRepository.findByUsername(
                request.getUsername()
        )).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )).thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("test-jwt-token");

        // Act
        ApiResponse<String> result =
                authenticationService.login(request);

        // Assert
        assertNotNull(result);
        assertEquals(
                "test-jwt-token",
                result.getData()
        );

        verify(userRepository)
                .findByUsername("testuser");

        verify(passwordEncoder)
                .matches(
                        "password123",
                        "encodedPassword"
                );

        verify(jwtService)
                .generateToken(user);
    }


    @Test
    void login_shouldThrowWhenUsernameDoesNotExist() {

        // Arrange
        AuthRequest request =
                createValidAuthRequest();

        when(userRepository.findByUsername(
                request.getUsername()
        )).thenReturn(Optional.empty());

        // Act & Assert
        AuthenticationException exception =
                assertThrows(
                        AuthenticationException.class,
                        () -> authenticationService.login(request)
                );

        assertEquals(
                "Invalid credentials",
                exception.getMessage()
        );

        verify(userRepository)
                .findByUsername("testuser");

        verify(passwordEncoder, never())
                .matches(anyString(), anyString());

        verify(jwtService, never())
                .generateToken(any());
    }


    @Test
    void login_shouldThrowWhenPasswordIsInvalid() {

        // Arrange
        AuthRequest request =
                createValidAuthRequest();

        User user =
                createUser();

        when(userRepository.findByUsername(
                request.getUsername()
        )).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )).thenReturn(false);

        // Act & Assert
        AuthenticationException exception =
                assertThrows(
                        AuthenticationException.class,
                        () -> authenticationService.login(request)
                );

        assertEquals(
                "Invalid credentials",
                exception.getMessage()
        );

        verify(userRepository)
                .findByUsername("testuser");

        verify(passwordEncoder)
                .matches(
                        "password123",
                        "encodedPassword"
                );

        verify(jwtService, never())
                .generateToken(any());
    }


    // =========================================================
    // TEST DATA HELPERS
    // =========================================================

    private RegisterRequest createValidRegisterRequest() {

        RegisterRequest request =
                new RegisterRequest();

        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        return request;
    }


    private AuthRequest createValidAuthRequest() {

        AuthRequest request =
                new AuthRequest();

        request.setUsername("testuser");
        request.setPassword("password123");

        return request;
    }


    private User createUser() {

        User user =
                new User();

        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.ADMIN);

        return user;
    }
}
