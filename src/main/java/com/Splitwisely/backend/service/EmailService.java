package com.Splitwisely.backend.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    // ------------------ VERIFICATION EMAIL ------------------
    public void sendVerificationEmail(String email, String verificationToken) {
        String subject = "Verify Your Email Address";
        String path = "/req/verify-email";
        String message = "Click the button below to verify your email address:";
        sendEmail(email, verificationToken, subject, path, message);
    }

    // ------------------ PASSWORD RESET EMAIL ------------------
    public void sendForgotPasswordEmail(String email, String resetToken) {
        String subject = "Password Reset Request";
        String path = "/req/reset-password";
        String message = "Click the button below to reset your password:";
        sendEmail(email, resetToken, subject, path, message);
    }

    // ------------------ GENERIC EMAIL BUILDER ------------------
    private void sendEmail(String email, String token, String subject, String path, String message) {
        try {
            String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path)
                    .queryParam("token", token)
                    .toUriString();

            String htmlContent = """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto;
                        padding: 20px; border-radius: 10px; background-color: #ffffff; border: 1px solid #eee;">
                    
                        <h2 style="color: #333; text-align:center;">%s</h2>
                        
                        <p style="font-size: 16px; color: #555; text-align:center;">%s</p>

                        <div style="text-align:center;">
                            <a href="%s" style="display: inline-block; margin: 20px 0;
                                padding: 12px 25px; font-size: 16px; color: #fff;
                                background-color: #007bff; text-decoration: none; border-radius: 6px;">
                                Proceed
                            </a>
                        </div>

                        <p style="font-size: 14px; color: #777; text-align:center;">Or copy this link:</p>
                        <p style="font-size: 14px; color: #007bff; text-align:center;">%s</p>

                        <p style="font-size: 12px; color: #aaa; text-align:center;">
                            This is an automated message. Please do not reply.
                        </p>
                    </div>
                    """.formatted(subject, message, actionUrl, actionUrl);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
