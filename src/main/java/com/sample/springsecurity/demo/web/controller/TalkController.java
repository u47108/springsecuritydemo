package com.sample.springsecurity.demo.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/talks")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
public class TalkController {

    @GetMapping()
    public String getTalk(){
        return "Hola LUXO";
    }
}
