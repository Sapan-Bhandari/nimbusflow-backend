package com.sapan.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication
        .UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority
        .SimpleGrantedAuthority;

import org.springframework.security.core.context
        .SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain)

            throws ServletException, IOException {

        // -----------------------------------
        // Skip JWT validation for public APIs
        // -----------------------------------

        String path = request.getServletPath();

        if (
                path.startsWith("/auth/")
                        ||
                        path.startsWith("/actuator/")
        ) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        // -----------------------------------
        // Read Authorization Header
        // -----------------------------------

        String authHeader =
                request.getHeader(
                        "Authorization"
                );

        if (
                authHeader == null
                        ||
                        !authHeader.startsWith("Bearer ")
        ) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        // -----------------------------------
        // Extract JWT Token
        // -----------------------------------

        String token =
                authHeader.substring(7);

        try {

            if (jwtUtil.validateToken(token)) {

                String email =
                        jwtUtil.extractEmail(token);

                UsernamePasswordAuthenticationToken
                        authentication =
                        new UsernamePasswordAuthenticationToken(

                                email,

                                null,

                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_USER"
                                        )
                                )
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                authentication
                        );
            }

        } catch (Exception e) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            return;
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}