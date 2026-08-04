package com.dylanclarke.springbootapitemplate.security;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dylanclarke.springbootapitemplate.model.Role;
import com.dylanclarke.springbootapitemplate.model.User;

import io.jsonwebtoken.ExpiredJwtException;


class JwtServiceTest {

    private JwtService jwtService;

    private static final String TEST_SECRET =
            "test-secret-key-for-jwt-unit-testing-must-be-at-least-32-bytes";

    private static final long TEST_EXPIRATION =
            60_000L;


    @BeforeEach
    void setUp() throws Exception {

        jwtService =
                new JwtService();

        setField(
                jwtService,
                "secret",
                TEST_SECRET
        );

        setField(
                jwtService,
                "expiration",
                TEST_EXPIRATION
        );

        jwtService.init();
    }


    // =========================================================
    // TOKEN GENERATION
    // =========================================================

    @Test
    void generateToken_shouldCreateValidToken() {

        // Arrange
        User user =
                createUser();

        // Act
        String token =
                jwtService.generateToken(user);

        // Assert
        assertNotNull(token);
        assertFalse(token.isBlank());
    }


    @Test
    void generateToken_shouldContainUsername() {

        // Arrange
        User user =
                createUser();

        // Act
        String token =
                jwtService.generateToken(user);

        // Assert
        assertEquals(
                "testuser",
                jwtService.extractUsername(token)
        );
    }


    @Test
    void generateToken_shouldContainRoleClaim() {

        // Arrange
        User user =
                createUser();

        // Act
        String token =
                jwtService.generateToken(user);

        // Assert
        String role =
                jwtService.extractClaim(
                        token,
                        claims -> claims.get("role", String.class)
                );

        assertEquals(
                Role.ADMIN.name(),
                role
        );
    }


    // =========================================================
    // CLAIM EXTRACTION
    // =========================================================

    @Test
    void extractUsername_shouldReturnUsernameFromToken() {

        // Arrange
        User user =
                createUser();

        String token =
                jwtService.generateToken(user);

        // Act
        String username =
                jwtService.extractUsername(token);

        // Assert
        assertEquals(
                "testuser",
                username
        );
    }


    @Test
    void extractExpiration_shouldReturnFutureExpirationDate() {

        // Arrange
        User user =
                createUser();

        String token =
                jwtService.generateToken(user);

        // Act
        Date expiration =
                jwtService.extractExpiration(token);

        // Assert
        assertNotNull(expiration);
        assertTrue(
                expiration.after(new Date())
        );
    }


    // =========================================================
    // EXPIRATION
    // =========================================================

    @Test
    void isTokenExpired_shouldReturnFalseForValidToken() {

        // Arrange
        User user =
                createUser();

        String token =
                jwtService.generateToken(user);

        // Act
        boolean expired =
                jwtService.isTokenExpired(token);

        // Assert
        assertFalse(expired);
    }

    @Test
    void isTokenExpired_shouldThrowWhenTokenHasExpired() throws Exception {

        // Arrange
        JwtService expiredJwtService =
                new JwtService();

        setField(
                expiredJwtService,
                "secret",
                TEST_SECRET
        );

        setField(
                expiredJwtService,
                "expiration",
                -1L
        );

        expiredJwtService.init();

        User user =
                createUser();

        String token =
                expiredJwtService.generateToken(user);

        // Act & Assert
        assertThrows(
                ExpiredJwtException.class,
                () -> expiredJwtService.isTokenExpired(token)
        );
    }

    // =========================================================
    // INVALID TOKEN
    // =========================================================

    @Test
    void extractUsername_shouldRejectTokenSignedWithWrongKey()
            throws Exception {

        // Arrange
        JwtService otherJwtService =
                new JwtService();

        setField(
                otherJwtService,
                "secret",
                "another-test-secret-key-for-jwt-unit-testing-must-be-32-bytes"
        );

        setField(
                otherJwtService,
                "expiration",
                TEST_EXPIRATION
        );

        otherJwtService.init();

        User user =
                createUser();

        String token =
                otherJwtService.generateToken(user);

        // Act & Assert
        assertThrows(
                Exception.class,
                () -> jwtService.extractUsername(token)
        );
    }


    // =========================================================
    // TEST DATA HELPERS
    // =========================================================

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


    private void setField(
            Object target,
            String fieldName,
            Object value
    ) throws Exception {

        Field field =
                target.getClass()
                        .getDeclaredField(fieldName);

        field.setAccessible(true);
        field.set(target, value);
    }
}
