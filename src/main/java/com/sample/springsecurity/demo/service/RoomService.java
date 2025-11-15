package com.sample.springsecurity.demo.service;

import com.sample.springsecurity.demo.dto.Room;
import com.sample.springsecurity.demo.repository.RoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Room Service - Improved with input validation and error handling
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    
    private final RoomRepository roomRepository;

    /**
     * Get all rooms
     */
    public List<Room> getRooms() {
        log.debug("Fetching all rooms");
        List<Room> rooms = roomRepository.findAll();
        log.info("Found {} rooms", rooms.size());
        return rooms;
    }

    /**
     * Create room with input validation
     */
    public void createRoom(Room room) {
        if (room == null) {
            log.warn("Attempted to create null room");
            throw new IllegalArgumentException("Room cannot be null");
        }
        
        log.debug("Creating room: {}", room);
        roomRepository.save(room);
        log.info("Room created successfully: {}", room);
    }
}
