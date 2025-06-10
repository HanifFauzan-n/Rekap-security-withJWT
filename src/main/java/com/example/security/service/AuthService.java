package com.example.security.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.dto.LoginDto;
import com.example.security.dto.RequestDto;
import com.example.security.dto.ResponseDto;
import com.example.security.entities.Role;
import com.example.security.entities.Users;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UsersRepository;
import com.example.security.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;

    public ResponseDto register(RequestDto dto) {
        if (usersRepository.findByUsername(dto.getUsername()).isPresent())
            throw new RuntimeException("Username Already exists");

        Role role = roleRepository.findByName("ROLE_USERS")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USERS")));

        Users user = new Users();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(role));

        usersRepository.save(user);

        String token = jwtUtils.generateToken(user.getUsername());
        String subject = "Selamat, Anda sekarang terhubung dengan layanan aplikasi kami";
        String text = "Hallo " + user.getUsername() + " , terimakasih untuk pendaftaran layanan aplikasi kami";

        emailService.sendSimpleMail(user.getEmail(), subject, text);

        return new ResponseDto(token);
    }

    public ResponseDto login(LoginDto dto) {
        Users user = usersRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Username tidak ditemukan"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password salah");
        }

        String token = jwtUtils.generateToken(user.getUsername());
        return new ResponseDto(token);
    }
}
