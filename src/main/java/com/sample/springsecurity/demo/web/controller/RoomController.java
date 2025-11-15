package com.sample.springsecurity.demo.web.controller;

import com.sample.springsecurity.demo.dto.Room;
import com.sample.springsecurity.demo.mapper.RoomMapper;
import com.sample.springsecurity.demo.service.RoomService;
import com.sample.springsecurity.demo.web.presentation.RoomDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Room Controller - Updated with input validation and error handling
 * Fix: Remove insecure CORS (origins: "*")
 * CORS is now configured in WebSecurity with configurable origins
 */
@Slf4j
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    /**
     * Get all rooms
     */
    @GetMapping
    public ResponseEntity<List<RoomDto>> getRooms() {
        try {
            List<Room> rooms = roomService.getRooms();
            List<RoomDto> roomDtos = rooms.stream()
                .map(roomMapper::convertToRoomDto)
                .collect(Collectors.toList());
            return ResponseEntity.ok(roomDtos);
        } catch (Exception e) {
            log.error("Error fetching rooms", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Create room with input validation
     */
    @PostMapping
    public ResponseEntity<Void> createRoom(@Valid @RequestBody RoomDto roomDto) {
        try {
            if (roomDto == null) {
                log.warn("Attempted to create room with null DTO");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            
            Room room = roomMapper.convertToRoom(roomDto);
            roomService.createRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            log.warn("Invalid room data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating room", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
