package com.yoanesber.graphql_employee_management.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/*
 * This class is responsible for filtering incoming HTTP requests to check for a valid API key.
 * It ensures that only requests with the correct API key can access the GraphQL endpoint.
 * If the API key is missing or invalid, it returns a 401 Unauthorized response.
 */

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public ApiKeyAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
        HttpServletResponse response, FilterChain filterChain) 
        throws ServletException, IOException {
                
        String requestApiKey = request.getHeader("X-API-KEY");

        if (requestApiKey == null || requestApiKey.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"error\": \"Unauthorized: Missing API key\"}");
            return;
        }

        if (requestApiKey != null) {
            ApiKeyAuthenticationToken authRequest = new ApiKeyAuthenticationToken(requestApiKey);
            Authentication authResult = authenticationManager.authenticate(authRequest);

            if (authResult == null || !authResult.isAuthenticated()) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"error\": \"Unauthorized: Invalid API key\"}");
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(authResult);
        }

        filterChain.doFilter(request, response);
    }
}
