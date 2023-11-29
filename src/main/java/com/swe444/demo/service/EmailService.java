package com.swe444.demo.service;

import com.swe444.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Password Reset");
        message.setText("Click on the following link to reset your password: http://localhost:8080/main/reset-password?token=" + token);

        // Inside your EmailService class
        try {
            // Your email sending code here
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            throw new RuntimeException("Error sending email");
        }

    }
}
