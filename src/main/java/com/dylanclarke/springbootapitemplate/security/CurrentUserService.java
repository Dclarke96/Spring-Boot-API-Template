package com.dylanclarke.springbootapitemplate.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class CurrentUserService {


    public CurrentUser get() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        if (auth == null
                || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof CustomUserDetails user)) {

            throw new AuthenticationCredentialsNotFoundException(
                    "No authenticated user found"
            );
        }


        return new CurrentUser(
                user.getId(),
                user.getUsername(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }


    public Long getUserId() {
        return get().getUserId();
    }

}
