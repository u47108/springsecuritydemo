package com.sample.springsecurity.demo.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication Response DTO
 * Updated to support both success (token) and error (message) responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private String token;
    private String message;

    /**
     * Constructor for success response with token
     */
    public AuthenticationResponse(String token) {
        this.token = token;
        this.message = null;
    }
}
