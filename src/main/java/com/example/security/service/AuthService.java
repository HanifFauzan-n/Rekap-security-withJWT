package com.example.security.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.dto.RequestDto;
import com.example.security.entities.Role;
import com.example.security.entities.Users;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(RequestDto dto) {
        if (usersRepository.findByUsername(dto.getUsername()).isPresent())
            throw new RuntimeException("Username Already exists");

        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USERS");

        Role role = optionalRole.orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USERS")));

        Users user = new Users();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.getRoles().add(role);

        usersRepository.save(user);
        return "User registered successfully";
    }
}
