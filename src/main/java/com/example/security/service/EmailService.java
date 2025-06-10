package com.example.security.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

public void sendWelcomeEmail(String to, String username) throws Exception {
    String htmlContent = loadEmailTemplate(username);
    sendHtmlEmail(to, "Selamat Datang di Aplikasi Kami", htmlContent);
}



}
