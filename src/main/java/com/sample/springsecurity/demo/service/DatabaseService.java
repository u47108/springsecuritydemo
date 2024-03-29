package com.sample.springsecurity.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sample.springsecurity.demo.dto.Room;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;
    private final RoomService roomService;

    public void fill() {
        clearDatabase();
        Room room1 = Room.builder().number(12L).build();
        roomService.createRoom(room1);
    }

    private void clearDatabase() {
        Stream<String> tablesScriptV1 = Stream.of("room", "speaker", "talk");
        tablesScriptV1.forEach(table -> jdbcTemplate.execute("delete from " + table));
    }
}
