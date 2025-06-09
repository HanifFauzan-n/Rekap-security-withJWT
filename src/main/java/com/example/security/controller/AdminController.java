package com.example.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @GetMapping("dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminAcces() {
        return ResponseEntity.ok("Ini Halaman Admin");
    }
    
}
