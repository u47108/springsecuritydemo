package com.sample.springsecurity.demo.mapper;

import com.sample.springsecurity.demo.dto.Room;
import com.sample.springsecurity.demo.web.presentation.RoomDto;

import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDto convertToRoomDto(Room room);

    Room convertToRoom(RoomDto roomDto);
}
