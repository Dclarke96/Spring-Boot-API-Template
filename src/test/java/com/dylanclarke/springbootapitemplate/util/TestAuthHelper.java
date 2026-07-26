package com.dylanclarke.springbootapitemplate.util;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestAuthHelper {

    public static String getAuthToken(
            MockMvc mockMvc,
            String username
    ) throws Exception {

        String registerJson = """
        {
          "username": "%s",
          "email": "%s@example.com",
          "password": "password"
        }
        """.formatted(
                username,
                username
        );

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson))
            .andExpect(status().isCreated());


        String loginJson = """
        {
          "username": "%s",
          "password": "password"
        }
        """.formatted(username);


        String response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();


        return response;
    }
}