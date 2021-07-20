package com.sample.springsecurity.demo.mapper;

import com.sample.springsecurity.demo.dto.Talk;
import com.sample.springsecurity.demo.web.presentation.TalkDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TalkMapper {

    TalkDto convertToTalkDto(Talk talk);

    Talk convertToTalk(TalkDto talkdto);

}
