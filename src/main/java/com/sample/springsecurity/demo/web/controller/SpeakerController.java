package com.sample.springsecurity.demo.web.controller;

import com.sample.springsecurity.demo.dto.Speaker;
import com.sample.springsecurity.demo.mapper.SpeakerMapper;
import com.sample.springsecurity.demo.service.SpeakerService;
import com.sample.springsecurity.demo.web.presentation.SpeakerDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Speaker Controller - Fix: Remove insecure CORS (origins: "*")
 * CORS is now configured in WebSecurity with configurable origins
 */
@RestController
@RequestMapping("/speakers")
@RequiredArgsConstructor
public class SpeakerController {

    private final SpeakerService speakerService;
    private final SpeakerMapper speakerMapper;

    @GetMapping
    public List<SpeakerDto> getSpeakers() {
        List<Speaker> speakers = speakerService.getSpeakers();
        return speakers.stream().map(speakerMapper::convertToSpeakerDto).collect(Collectors.toList());
    }
}
