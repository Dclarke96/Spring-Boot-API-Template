package com.dylanclarke.springbootapitemplate.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;
import io.jsonwebtoken.MalformedJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dylanclarke.springbootapitemplate.model.Role;
import com.dylanclarke.springbootapitemplate.model.User;
import com.dylanclarke.springbootapitemplate.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private JwtAuthFilter jwtAuthFilter;


    @BeforeEach
    void setUp() {

        jwtAuthFilter =
                new JwtAuthFilter(
                        jwtService,
                        userRepository,
                        authenticationEntryPoint
                );
    }


    @AfterEach
    void tearDown() {

        SecurityContextHolder.clearContext();
    }


    // =========================================================
    // SHOULD NOT FILTER
    // =========================================================

    @Test
    void shouldNotFilter_shouldReturnTrueForAuthEndpoint()
            throws Exception {

        // Arrange
        when(request.getServletPath())
                .thenReturn("/api/auth/login");

        // Act
        boolean result =
                jwtAuthFilter.shouldNotFilter(request);

        // Assert
        assertTrue(result);
    }


    @Test
    void shouldNotFilter_shouldReturnFalseForProtectedEndpoint()
            throws Exception {

        // Arrange
        when(request.getServletPath())
                .thenReturn("/api/vehicles");

        // Act
        boolean result =
                jwtAuthFilter.shouldNotFilter(request);

        // Assert
        assertFalse(result);
    }


    // =========================================================
    // JWT EXTRACTION
    // =========================================================

    @Test
    void doFilterInternal_shouldContinueWhenAuthorizationHeaderMissing()
            throws ServletException, IOException {

        // Arrange
        when(request.getHeader("Authorization"))
                .thenReturn(null);

        // Act
        jwtAuthFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        verify(filterChain).doFilter(
                request,
                response
        );

        verifyNoInteractions(jwtService);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(authenticationEntryPoint);
    }


    @Test
    void doFilterInternal_shouldContinueWhenAuthorizationHeaderIsNotBearer()
            throws ServletException, IOException {

        // Arrange
        when(request.getHeader("Authorization"))
                .thenReturn("Basic abc123");

        // Act
        jwtAuthFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        verify(filterChain).doFilter(
                request,
                response
        );

        verifyNoInteractions(jwtService);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(authenticationEntryPoint);
    }


    // =========================================================
    // SUCCESSFUL AUTHENTICATION
    // =========================================================

    @Test
    void doFilterInternal_shouldAuthenticateUserWithValidToken()
            throws ServletException, IOException {

        // Arrange
        String token = "valid.jwt.token";
        String username = "test@example.com";

        User user =
                createUser();

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token))
                .thenReturn(username);

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        // Act
        jwtAuthFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        assertNotNull(authentication);

        assertTrue(
                authentication.getPrincipal()
                        instanceof CustomUserDetails
        );

        CustomUserDetails principal =
                (CustomUserDetails)
                        authentication.getPrincipal();

        assertEquals(
                1L,
                principal.getId()
        );

        assertEquals(
                "test@example.com",
                principal.getUsername()
        );

        assertEquals(
                1,
                authentication.getAuthorities().size()
        );

        assertEquals(
                "ROLE_ADMIN",
                authentication.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
        );

        verify(filterChain).doFilter(
                request,
                response
        );

        verify(authenticationEntryPoint, never())
                .commence(
                        any(),
                        any(),
                        any()
                );
    }


    @Test
    void doFilterInternal_shouldNotReplaceExistingAuthentication()
            throws ServletException, IOException {

        // Arrange
        Authentication existingAuthentication =
                new UsernamePasswordAuthenticationToken(
                        "existing-user",
                        null
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        existingAuthentication
                );

        String token = "valid.jwt.token";

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token))
                .thenReturn("test@example.com");

        // Act
        jwtAuthFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        assertSame(
                existingAuthentication,
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );

        verifyNoInteractions(userRepository);

        verify(filterChain).doFilter(
                request,
                response
        );
    }


    // =========================================================
    // USER NOT FOUND
    // =========================================================

    @Test
    void doFilterInternal_shouldRejectWhenUserDoesNotExist()
            throws ServletException, IOException {

        // Arrange
        String token = "valid.jwt.token";
        String username = "missing@example.com";

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token))
                .thenReturn(username);

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        // Act
        jwtAuthFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        ArgumentCaptor<BadCredentialsException> captor =
                ArgumentCaptor.forClass(
                        BadCredentialsException.class
                );

        verify(authenticationEntryPoint)
                .commence(
                        eq(request),
                        eq(response),
                        captor.capture()
                );

        assertEquals(
                "User not found",
                captor.getValue().getMessage()
        );

        verify(filterChain, never())
                .doFilter(
                        request,
                        response
                );

        assertNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );
    }


    // =========================================================
    // INVALID JWT
    // =========================================================

    @Test
    void doFilterInternal_shouldRejectInvalidToken()
            throws ServletException, IOException {

        // Arrange
        String token = "invalid.jwt.token";

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token))
                .thenThrow(
                        new MalformedJwtException(
                                "Invalid token"
                        )
                );

        // Act
        jwtAuthFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        // Assert
        ArgumentCaptor<BadCredentialsException> captor =
                ArgumentCaptor.forClass(
                        BadCredentialsException.class
                );

        verify(authenticationEntryPoint)
                .commence(
                        eq(request),
                        eq(response),
                        captor.capture()
                );

        assertEquals(
                "Invalid JWT token",
                captor.getValue().getMessage()
        );

        verify(filterChain, never())
                .doFilter(
                        request,
                        response
                );
    }


    // =========================================================
    // HELPERS
    // =========================================================

    private User createUser() {

        User user =
                new User();

        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.ADMIN);

        return user;
    }
}
