package com.sample.springsecurity.demo.mapper;

import com.sample.springsecurity.demo.dto.Room;
import com.sample.springsecurity.demo.web.presentation.RoomDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomDto convertToRoomDto(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomDto roomDto = new RoomDto();

        roomDto.setId( room.getId() );
        roomDto.setNumber( room.getNumber() );

        return roomDto;
    }

    @Override
    public Room convertToRoom(RoomDto roomDto) {
        if ( roomDto == null ) {
            return null;
        }

        Room room = new Room();

        room.setId( roomDto.getId() );
        room.setNumber( roomDto.getNumber() );

        return room;
    }
}
