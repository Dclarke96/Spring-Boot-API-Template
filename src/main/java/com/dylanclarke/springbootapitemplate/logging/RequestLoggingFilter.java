package com.dylanclarke.springbootapitemplate.logging;

import com.dylanclarke.springbootapitemplate.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Component
public class RequestLoggingFilter extends OncePerRequestFilter {


    private static final Logger log =
            LoggerFactory.getLogger(RequestLoggingFilter.class);



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException {


        long startTime = System.currentTimeMillis();


        String traceId =
                UUID.randomUUID().toString();


        request.setAttribute(
                "traceId",
                traceId
        );


        log.info(
                "REQUEST_START traceId={} method={} uri={}",
                traceId,
                request.getMethod(),
                request.getRequestURI()
        );


        try {

            filterChain.doFilter(
                    request,
                    response
            );

        } finally {


            String userId =
                    "anonymous";


            var authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();


            if(authentication != null
                    && authentication.getPrincipal()
                    instanceof CustomUserDetails user) {

                userId =
                        String.valueOf(user.getId());
            }


            long duration =
                    System.currentTimeMillis()
                            - startTime;


            log.info(
                    "REQUEST_COMPLETE traceId={} method={} uri={} status={} durationMs={} userId={}",
                    traceId,
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration,
                    userId
            );
        }
    }
}