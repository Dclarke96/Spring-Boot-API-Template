package com.dylanclarke.springbootapitemplate.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthIntegrationTest extends BaseIntegrationTest {


    // =========================================================
    // REGISTER TESTS
    // =========================================================

    @Test
    @DisplayName("Should register a new user")
    void shouldRegisterUserSuccessfully() throws Exception {

        // Arrange

        String username = "user1";


        // Act

        register(username);


        // Assert

        Assertions.assertTrue(
                userRepository.findAll()
                        .stream()
                        .anyMatch(user ->
                                user.getUsername()
                                        .equals(username))
        );
    }


    @Test
    @DisplayName("Should reject duplicate username")
    void shouldRejectDuplicateUsername() throws Exception {

        // Arrange

        register("duplicate");

        String json = """
        {
          "username": "duplicate",
          "email": "duplicate@example.com",
          "password": "password"
        }
        """;


        // Act

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))


                // Assert

                .andExpect(status().isConflict());
    }


    @Test
    @DisplayName("Should reject duplicate email")
    void shouldRejectDuplicateEmail() throws Exception {

        // Arrange

        register(
                "user1",
                "shared@example.com",
                "password"
        );

        String json = """
        {
          "username": "user2",
          "email": "shared@example.com",
          "password": "password"
        }
        """;


        // Act

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))


                // Assert

                .andExpect(status().isConflict());
    }



    // =========================================================
    // LOGIN TESTS
    // =========================================================

    @Test
    @DisplayName("Should login successfully")
    void shouldLoginSuccessfully() throws Exception {

        // Arrange

        register("loginuser");


        // Act

        String token = login("loginuser");


        // Assert

        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());
    }


    @Test
    @DisplayName("Should reject invalid password")
    void shouldRejectInvalidPassword() throws Exception {

        // Arrange

        register("badpass");

        String json = """
        {
          "username": "badpass",
          "password": "wrongpassword"
        }
        """;


        // Act

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))


                // Assert

                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("Should reject unknown user")
    void shouldRejectUnknownUser() throws Exception {

        // Arrange

        String json = """
        {
          "username": "doesnotexist",
          "password": "password"
        }
        """;


        // Act

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))


                // Assert

                .andExpect(status().isUnauthorized());
    }



    // =========================================================
    // AUTHENTICATION TESTS
    // =========================================================

    @Test
    @DisplayName("Should allow authenticated request")
    void shouldAllowAuthenticatedRequest() throws Exception {

        // Arrange

        String token = authenticate("authuser");


        // Act

        mockMvc.perform(get("/api/vehicles")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))


                // Assert

                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Should reject invalid JWT")
    void shouldRejectInvalidJwt() throws Exception {

        // Arrange

        String token = "invalidtoken";


        // Act

        mockMvc.perform(get("/api/vehicles")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))


                // Assert

                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("Should reject expired JWT")
    void shouldRejectExpiredJwt() throws Exception {

        // Arrange

        String expiredToken =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.expired.signature";


        // Act

        mockMvc.perform(get("/api/vehicles")
                        .header(
                                "Authorization",
                                "Bearer " + expiredToken
                        ))


                // Assert

                .andExpect(status().isUnauthorized());
    }
}