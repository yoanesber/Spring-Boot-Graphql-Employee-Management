package com.yoanesber.graphql_employee_management.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class is responsible for configuring security settings for the application.
 * It uses Spring Security to manage authentication and authorization for the GraphQL endpoint.
 * The ApiKeyAuthFilter is used to validate API keys for incoming requests.
 */

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, 
        AuthenticationManager authManager) throws Exception {
        ApiKeyAuthFilter apiKeyAuthFilter = new ApiKeyAuthFilter(authManager);

        http
            .csrf(csrf -> csrf
                .disable()) // Disable CSRF protection because the API is stateless (no sessions) and JWT is stored in HTTP headers (not cookies)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set the session creation policy to stateless (no sessions)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/graphql").authenticated() // Only allow authenticated requests to the /graphql endpoint
                .anyRequest().permitAll())
            .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add the ApiKeyAuthFilter before the UsernamePasswordAuthenticationFilter
    
        return http.build();
    }

}