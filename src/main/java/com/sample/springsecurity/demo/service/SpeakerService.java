package com.sample.springsecurity.demo.service;

import com.sample.springsecurity.demo.dto.Speaker;
import com.sample.springsecurity.demo.repository.SpeakerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakerService {

    private final SpeakerRepository speakerRepository;

    public List<Speaker> getSpeakers() {
        return speakerRepository.findAll();
    }
}
