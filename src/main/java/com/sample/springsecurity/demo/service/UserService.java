package com.sample.springsecurity.demo.service;

import com.sample.springsecurity.demo.dto.User;
import com.sample.springsecurity.demo.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }
}
