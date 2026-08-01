package com.dylanclarke.springbootapitemplate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dylanclarke.springbootapitemplate.api.ApiResponse;
import com.dylanclarke.springbootapitemplate.dto.AuthRequest;
import com.dylanclarke.springbootapitemplate.dto.RegisterRequest;
import com.dylanclarke.springbootapitemplate.service.AuthenticationService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

@Mock
private AuthenticationService authenticationService;

private AuthController authController;

@BeforeEach
void setUp() {
    authController =
            new AuthController(authenticationService);
}


// =========================================================
// REGISTER
// =========================================================

@Test
void register_shouldReturnCreatedResponse() {

    // Arrange
    RegisterRequest request =
            createRegisterRequest();

    ApiResponse<String> serviceResponse =
            ApiResponse.success(
                    null,
                    "User registered successfully"
            );

    when(authenticationService.register(request))
            .thenReturn(serviceResponse);

    // Act
    ResponseEntity<ApiResponse<String>> result =
            authController.register(request);

    // Assert
    assertNotNull(result);
    assertEquals(
            HttpStatus.CREATED,
            result.getStatusCode()
    );
    assertEquals(
            serviceResponse,
            result.getBody()
    );

    verify(authenticationService)
            .register(request);
}


// =========================================================
// LOGIN
// =========================================================

@Test
void login_shouldReturnOkForSuccessfulAuthentication() {

    // Arrange
    AuthRequest request =
            createAuthRequest();

    ApiResponse<String> serviceResponse =
            ApiResponse.success(
                    "test-jwt-token",
                    "Login successful"
            );

    when(authenticationService.login(request))
            .thenReturn(serviceResponse);

    // Act
    ResponseEntity<ApiResponse<String>> result =
            authController.login(request);

    // Assert
    assertNotNull(result);
    assertEquals(
            HttpStatus.OK,
            result.getStatusCode()
    );
    assertEquals(
            serviceResponse,
            result.getBody()
    );

    verify(authenticationService)
            .login(request);
}


@Test
void login_shouldReturnUnauthorizedWhenAuthenticationFails() {

    // Arrange
    AuthRequest request =
            createAuthRequest();

    @SuppressWarnings("unchecked")
    ApiResponse<String> serviceResponse =
            org.mockito.Mockito.mock(ApiResponse.class);

    when(serviceResponse.isSuccess())
            .thenReturn(false);

    when(authenticationService.login(request))
            .thenReturn(serviceResponse);

    // Act
    ResponseEntity<ApiResponse<String>> result =
            authController.login(request);

    // Assert
    assertNotNull(result);
    assertEquals(
            HttpStatus.UNAUTHORIZED,
            result.getStatusCode()
    );
    assertEquals(
            serviceResponse,
            result.getBody()
    );

    verify(authenticationService)
            .login(request);
}


// =========================================================
// TEST DATA
// =========================================================

private RegisterRequest createRegisterRequest() {

    RegisterRequest request =
            new RegisterRequest();

    request.setUsername("testuser");
    request.setEmail("test@example.com");
    request.setPassword("Password123!");

    return request;
}


private AuthRequest createAuthRequest() {

    AuthRequest request =
            new AuthRequest();

    request.setUsername("testuser");
    request.setPassword("Password123!");

    return request;
}


}
