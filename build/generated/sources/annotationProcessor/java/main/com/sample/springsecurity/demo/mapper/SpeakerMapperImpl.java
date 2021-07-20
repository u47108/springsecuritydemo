package com.sample.springsecurity.demo.mapper;

import com.sample.springsecurity.demo.dto.Speaker;
import com.sample.springsecurity.demo.web.presentation.SpeakerDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class SpeakerMapperImpl implements SpeakerMapper {

    @Override
    public SpeakerDto convertToSpeakerDto(Speaker speaker) {
        if ( speaker == null ) {
            return null;
        }

        SpeakerDto speakerDto = new SpeakerDto();

        speakerDto.setId( speaker.getId() );
        speakerDto.setName( speaker.getName() );

        return speakerDto;
    }

    @Override
    public Speaker convertToSpeaker(SpeakerDto speakerDto) {
        if ( speakerDto == null ) {
            return null;
        }

        Speaker speaker = new Speaker();

        speaker.setId( speakerDto.getId() );
        speaker.setName( speakerDto.getName() );

        return speaker;
    }
}
