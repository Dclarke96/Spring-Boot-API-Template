package com.dylanclarke.springbootapitemplate.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


class CurrentUserServiceTest {

    private CurrentUserService currentUserService;


    @BeforeEach
    void setUp() {

        currentUserService =
                new CurrentUserService();
    }


    @AfterEach
    void tearDown() {

        SecurityContextHolder.clearContext();
    }


    // =========================================================
    // GET CURRENT USER
    // =========================================================

    @Test
    void get_shouldReturnCurrentAuthenticatedUser() {

        // Arrange
        CustomUserDetails userDetails =
                createUserDetails();

        setAuthentication(userDetails);

        // Act
        CurrentUser result =
                currentUserService.get();

        // Assert
        assertNotNull(result);

        assertEquals(
                1L,
                result.getUserId()
        );

        assertEquals(
                "test@example.com",
                result.getEmail()
        );

        assertEquals(
                List.of("ROLE_ADMIN"),
                result.getRoles()
        );
    }


    @Test
    void get_shouldMapMultipleRoles() {

        // Arrange
        List<GrantedAuthority> authorities =
                List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER")
                );

        CustomUserDetails userDetails =
                new CustomUserDetails(
                        1L,
                        "test@example.com",
                        "encodedPassword",
                        authorities
                );

        setAuthentication(userDetails);

        // Act
        CurrentUser result =
                currentUserService.get();

        // Assert
        assertEquals(
                List.of(
                        "ROLE_ADMIN",
                        "ROLE_USER"
                ),
                result.getRoles()
        );
    }


    // =========================================================
    // GET USER ID
    // =========================================================

    @Test
    void getUserId_shouldReturnCurrentUserId() {

        // Arrange
        CustomUserDetails userDetails =
                createUserDetails();

        setAuthentication(userDetails);

        // Act
        Long result =
                currentUserService.getUserId();

        // Assert
        assertEquals(
                1L,
                result
        );
    }


    // =========================================================
    // TEST HELPERS
    // =========================================================

    private CustomUserDetails createUserDetails() {

        return new CustomUserDetails(
                1L,
                "test@example.com",
                "encodedPassword",
                List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                )
        );
    }


    private void setAuthentication(
            CustomUserDetails userDetails
    ) {

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }
}
