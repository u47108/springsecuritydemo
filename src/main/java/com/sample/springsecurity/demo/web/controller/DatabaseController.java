package com.sample.springsecurity.demo.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springsecurity.demo.service.DatabaseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/database/populate")
public class DatabaseController {

    private final DatabaseService databaseService;

    @PostMapping
    public void populateDatabase(){
        databaseService.fill();
    }
}
