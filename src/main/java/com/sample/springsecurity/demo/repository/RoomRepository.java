package com.sample.springsecurity.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.springsecurity.demo.dto.Room;


public interface RoomRepository extends JpaRepository<Room, Long> {

}
