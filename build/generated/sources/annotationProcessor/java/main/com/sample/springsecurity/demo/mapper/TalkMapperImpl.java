package com.sample.springsecurity.demo.mapper;

import com.sample.springsecurity.demo.dto.Room;
import com.sample.springsecurity.demo.dto.Speaker;
import com.sample.springsecurity.demo.dto.Talk;
import com.sample.springsecurity.demo.web.presentation.RoomDto;
import com.sample.springsecurity.demo.web.presentation.SpeakerDto;
import com.sample.springsecurity.demo.web.presentation.TalkDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class TalkMapperImpl implements TalkMapper {

    @Override
    public TalkDto convertToTalkDto(Talk talk) {
        if ( talk == null ) {
            return null;
        }

        TalkDto talkDto = new TalkDto();

        talkDto.setId( talk.getId() );
        talkDto.setName( talk.getName() );
        talkDto.setDate( talk.getDate() );
        talkDto.setSpeaker( speakerToSpeakerDto( talk.getSpeaker() ) );
        talkDto.setRoom( roomToRoomDto( talk.getRoom() ) );

        return talkDto;
    }

    @Override
    public Talk convertToTalk(TalkDto talkdto) {
        if ( talkdto == null ) {
            return null;
        }

        Talk talk = new Talk();

        talk.setId( talkdto.getId() );
        talk.setName( talkdto.getName() );
        talk.setDate( talkdto.getDate() );
        talk.setSpeaker( speakerDtoToSpeaker( talkdto.getSpeaker() ) );
        talk.setRoom( roomDtoToRoom( talkdto.getRoom() ) );

        return talk;
    }

    protected SpeakerDto speakerToSpeakerDto(Speaker speaker) {
        if ( speaker == null ) {
            return null;
        }

        SpeakerDto speakerDto = new SpeakerDto();

        speakerDto.setId( speaker.getId() );
        speakerDto.setName( speaker.getName() );

        return speakerDto;
    }

    protected RoomDto roomToRoomDto(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomDto roomDto = new RoomDto();

        roomDto.setId( room.getId() );
        roomDto.setNumber( room.getNumber() );

        return roomDto;
    }

    protected Speaker speakerDtoToSpeaker(SpeakerDto speakerDto) {
        if ( speakerDto == null ) {
            return null;
        }

        Speaker speaker = new Speaker();

        speaker.setId( speakerDto.getId() );
        speaker.setName( speakerDto.getName() );

        return speaker;
    }

    protected Room roomDtoToRoom(RoomDto roomDto) {
        if ( roomDto == null ) {
            return null;
        }

        Room room = new Room();

        room.setId( roomDto.getId() );
        room.setNumber( roomDto.getNumber() );

        return room;
    }
}
