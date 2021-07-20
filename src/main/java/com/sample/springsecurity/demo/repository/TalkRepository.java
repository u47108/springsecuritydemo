package com.sample.springsecurity.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.springsecurity.demo.dto.Talk;

public interface TalkRepository extends JpaRepository<Talk, Long> {
}
