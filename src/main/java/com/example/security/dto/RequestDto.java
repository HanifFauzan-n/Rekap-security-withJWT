package com.example.security.dto;

import lombok.Data;

@Data
public class RequestDto {
    private String username;
    private String email;
    private String password;
}
