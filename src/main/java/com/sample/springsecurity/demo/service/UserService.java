package com.sample.springsecurity.demo.service;

import com.sample.springsecurity.demo.dto.User;
import com.sample.springsecurity.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * User Service - Improved with input validation and error handling
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    /**
     * Save user with input validation
     */
    public void save(User user) {
        if (user == null) {
            log.warn("Attempted to save null user");
            throw new IllegalArgumentException("User cannot be null");
        }
        
        log.debug("Saving user: {}", user);
        userRepository.save(user);
        log.info("User saved successfully: {}", user);
    }
}
