package com.example.security.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.dto.LoginDto;
import com.example.security.dto.RequestDto;
import com.example.security.dto.ResetPasswordRequest;
import com.example.security.dto.ResetRequest;
import com.example.security.dto.ResponseDto;
import com.example.security.entities.Users;
import com.example.security.repositories.UsersRepository;
import com.example.security.service.AuthService;
import com.example.security.service.EmailService;
import com.example.security.utils.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/auth/")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UsersRepository usersRepository;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody RequestDto dto) {

        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginDto dto) {

        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("request-reset-password")
    public ResponseEntity<String> requestReset(@RequestBody ResetRequest request) {
        Optional<Users> user = usersRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email tidak ditemukan.");
        }

        try {
            String token = jwtUtils.generateToken(user.get().getUsername());
            String link = "http://localhost:3000/reset-password?token=" + token;

            log.info(link + "\n" + token);
            emailService.sendResetPasswordEmail(user.get().getEmail(), link);
            return ResponseEntity.ok("Email reset telah dikirim.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Terjadi kesalahan saat mengirim email.");
        }
    }

    @PostMapping("reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String username;
        username = jwtUtils.extractUsername(request.getToken());

        if (username != null && jwtUtils.isTokenValid(request.getToken())) {
            Users user = usersRepository.findByUsername(username).orElseThrow();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            usersRepository.save(user);

            return ResponseEntity.ok("Password berhasil diubah.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token tidak valid atau sudah expired.");
    }

}
