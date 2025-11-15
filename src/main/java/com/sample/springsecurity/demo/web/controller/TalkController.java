package com.sample.springsecurity.demo.web.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Talk Controller - Fix: Remove insecure CORS (origins: "*")
 * CORS is now configured in WebSecurity with configurable origins
 */
@RestController
@RequestMapping("/talks")
public class TalkController {

    @GetMapping()
    public String getTalk(){
        return "Hola LUXO";
    }
}
