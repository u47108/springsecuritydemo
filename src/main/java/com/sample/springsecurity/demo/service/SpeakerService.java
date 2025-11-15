package com.sample.springsecurity.demo.service;

import com.sample.springsecurity.demo.dto.Speaker;
import com.sample.springsecurity.demo.repository.SpeakerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Speaker Service - Improved with logging
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SpeakerService {

    private final SpeakerRepository speakerRepository;

    /**
     * Get all speakers
     */
    public List<Speaker> getSpeakers() {
        log.debug("Fetching all speakers");
        List<Speaker> speakers = speakerRepository.findAll();
        log.info("Found {} speakers", speakers.size());
        return speakers;
    }
}
