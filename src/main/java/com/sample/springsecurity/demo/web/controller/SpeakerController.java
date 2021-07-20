package com.sample.springsecurity.demo.web.controller;

import com.sample.springsecurity.demo.dto.Speaker;
import com.sample.springsecurity.demo.mapper.SpeakerMapper;
import com.sample.springsecurity.demo.service.SpeakerService;
import com.sample.springsecurity.demo.web.presentation.SpeakerDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/speakers")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
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
