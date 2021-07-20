package com.sample.springsecurity.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.springsecurity.demo.dto.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
