package com.example.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExampleController {
    
    @GetMapping("/hello")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Halo! Kamu sudah login.");
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> userAcces() {
        return ResponseEntity.ok("Hello, Ini Halaman Profile");
    }
    
}
