package com.sample.springsecurity.demo.web.controller;

import com.sample.springsecurity.demo.dto.Room;
import com.sample.springsecurity.demo.mapper.RoomMapper;
import com.sample.springsecurity.demo.service.RoomService;
import com.sample.springsecurity.demo.web.presentation.RoomDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping
    public List<RoomDto> getRooms() {
        List<Room> rooms = roomService.getRooms();
        return rooms.stream().map(roomMapper::convertToRoomDto).collect(Collectors.toList());
    }

    @PostMapping
    public void createRoom(@RequestBody RoomDto roomDto) {
        Room room = roomMapper.convertToRoom(roomDto);
        roomService.createRoom(room);
    }
}
