package com.school.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        // TODO: Integrate with SendGrid/AWS SES for production
        log.info("Sending Email to {}: Subject: {}", to, subject);
    }
}