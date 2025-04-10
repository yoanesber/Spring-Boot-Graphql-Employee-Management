package com.yoanesber.graphql_employee_management.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/*
 * This class is responsible for filtering incoming HTTP requests to check for a valid API key.
 * It ensures that only requests with the correct API key can access the GraphQL endpoint.
 * If the API key is missing or invalid, it returns a 401 Unauthorized response.
 */

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${app.api.key}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        String requestApiKey = request.getHeader("X-API-KEY");

        if ("/graphql".equals(request.getRequestURI())) {
            if (requestApiKey == null || !requestApiKey.equals(apiKey)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"error\": \"Unauthorized: Invalid or missing API key\"}");
                return;
            }
        }
        // Allow other endpoints to be accessed without API key
        // You can add more logic here if needed for other endpoints

        filterChain.doFilter(request, response);

    }
}
