package com.dylanclarke.springbootapitemplate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
        description = "Request payload used to register a new user account."
)
public class RegisterRequest {

    @Schema(
            description = "Username for the new account.",
            example = "admin"
    )
    @NotBlank(message = "Username cannot be blank")
    @Size(
            min = 3,
            max = 20,
            message = "Username must be between 3 and 20 characters"
    )
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Username can only contain letters, numbers, and underscores"
    )
    private String username;

    @Schema(
            description = "Email address for the new account.",
            example = "admin@example.com"
    )
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(
            description = "Password for the new account. Must be at least 8 characters.",
            format = "password"
    )
    @NotBlank(message = "Password cannot be blank")
    @Size(
            min = 8,
            message = "Password must be at least 8 characters"
    )
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
