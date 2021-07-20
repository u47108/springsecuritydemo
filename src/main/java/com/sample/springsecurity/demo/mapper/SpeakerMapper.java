package com.sample.springsecurity.demo.mapper;

import com.sample.springsecurity.demo.dto.Speaker;
import com.sample.springsecurity.demo.web.presentation.SpeakerDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeakerMapper {
    SpeakerDto convertToSpeakerDto(Speaker speaker);

    Speaker convertToSpeaker(SpeakerDto speakerDto);
}
