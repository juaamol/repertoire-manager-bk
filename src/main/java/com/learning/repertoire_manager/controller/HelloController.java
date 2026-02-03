package com.learning.repertoire_manager.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(
            @RequestParam(defaultValue = "World") String name
    ) {
        return "Hello " + name + "!";
    }
}
