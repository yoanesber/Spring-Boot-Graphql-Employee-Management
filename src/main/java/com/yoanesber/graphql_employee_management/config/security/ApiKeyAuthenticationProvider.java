package com.yoanesber.graphql_employee_management.config.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/*
 * This class is responsible for providing authentication based on API keys.
 * It checks if the provided API key matches the expected API key from the application properties.
 * If the API key is valid, it returns an authenticated token; otherwise, it returns null.
 * This class implements the AuthenticationProvider interface from Spring Security.
 */
@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {
    @Value("${spring.graphql.api-key}")
    private String expectedApiKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = (String) authentication.getCredentials();

        if (expectedApiKey.equals(apiKey)) {
            return new ApiKeyAuthenticationToken(apiKey, List.of(() -> "ROLE_USER"));
        }

        return new ApiKeyAuthenticationToken(apiKey);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}