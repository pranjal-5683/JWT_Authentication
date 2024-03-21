package com.adminbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class MessageController {

    @GetMapping("/message")
    public ResponseEntity<?> getMessage(){
        return ResponseEntity.ok("Hello, Welcome to the dashboard");
    }
}
