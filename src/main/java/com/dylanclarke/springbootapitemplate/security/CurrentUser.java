package com.dylanclarke.springbootapitemplate.security;

import java.util.List;

public class CurrentUser {

    private final Long userId;
    private final String email;
    private final List<String> roles;


    public CurrentUser(
            Long userId,
            String email,
            List<String> roles
    ) {
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }


    public Long getUserId() {
        return userId;
    }


    public String getEmail() {
        return email;
    }


    public List<String> getRoles() {
        return roles;
    }
}