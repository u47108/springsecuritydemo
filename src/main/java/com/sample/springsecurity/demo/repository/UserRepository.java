package com.sample.springsecurity.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.springsecurity.demo.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
