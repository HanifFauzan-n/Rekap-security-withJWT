package com.example.security.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true = HTML
        helper.setFrom("your-email@gmail.com");

        mailSender.send(message);
    }

    public String loadEmailTemplate(String username) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("templates/email-welcome.html");
        assert is != null;
        String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        return template.replace("[[USERNAME]]", username);
    }

    public String loadEmailResetPassword(String token) throws IOException {
        String path = "src/main/resources/templates/reset-password-email.html";
        String html = Files.readString(Path.of(path));
        String resetLink = "http://localhost:8080/reset-password.html?token=" + token;
        return html.replace("${resetLink}", resetLink);
    }

    public String loadEmailVerified(String token) throws Exception {
        String path = "src/main/resources/templates/verify-email.html";
        String html = Files.readString(Path.of(path));
        String verifyLink = "http://localhost:8080/api/auth/verify?token=" + token;
        return html.replace("${link}", verifyLink);
    }

    public void sendVerifyEmail(String to, String token) throws Exception {
        String htmlContent = loadEmailVerified(token);
        sendHtmlEmail(to,"Verifikasi Email Anda", htmlContent);
    }

    public void sendResetPasswordEmail(String to, String token) throws Exception {
        String htmlContent = loadEmailResetPassword(token);
        log.info(htmlContent);
        sendHtmlEmail(to, "PERINGATAN - reset password", htmlContent);
    }

    public void sendWelcomeEmail(String to, String username) throws Exception {
        String htmlContent = loadEmailTemplate(username);
        sendHtmlEmail(to, "Selamat Datang di Aplikasi Kami", htmlContent);
    }

}
