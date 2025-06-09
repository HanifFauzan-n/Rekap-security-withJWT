package com.example.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @GetMapping("/user")
    public ResponseEntity<String> userAcces() {
        return ResponseEntity.ok("Hello User");
    }
    
}
