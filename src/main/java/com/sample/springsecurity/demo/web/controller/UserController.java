package com.sample.springsecurity.demo.web.controller;

import com.sample.springsecurity.demo.security.AuthenticationRequest;
import com.sample.springsecurity.demo.security.AuthenticationResponse;
import com.sample.springsecurity.demo.security.JwtService;
import com.sample.springsecurity.demo.service.MyUserDetailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller - Updated with improved security and error handling
 * Fix: Remove insecure CORS (origins: "*")
 * CORS is now configured in WebSecurity with configurable origins
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailService myUserDetailService;
    private final JwtService jwtService;

    /**
     * Authenticate user and generate JWT token
     * Improved: Added input validation and better error handling
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createToken(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        
        // Validate input
        if (authenticationRequest == null || 
            authenticationRequest.getUsername() == null || 
            authenticationRequest.getUsername().trim().isEmpty() ||
            authenticationRequest.getPassword() == null || 
            authenticationRequest.getPassword().trim().isEmpty()) {
            log.warn("Invalid authentication request: null or empty credentials");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            String username = authenticationRequest.getUsername().trim();
            String password = authenticationRequest.getPassword();
            
            // Authenticate user
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authentication);
            
            // Load user details
            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
            
            // Generate JWT token
            String token = jwtService.createToken(userDetails);
            
            log.info("User {} authenticated successfully", username);
            return ResponseEntity.ok(new AuthenticationResponse(token));
            
        } catch (BadCredentialsException e) {
            log.warn("Authentication failed for user: {}", authenticationRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthenticationResponse("Invalid username or password"));
        } catch (UsernameNotFoundException e) {
            log.warn("User not found: {}", authenticationRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthenticationResponse("User not found"));
        } catch (Exception e) {
            log.error("Error during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthenticationResponse("Authentication error occurred"));
        }
    }

    /**
     * Test endpoint
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }
}
