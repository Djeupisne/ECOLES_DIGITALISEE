package com.school.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsService {
    public void sendSms(String phoneNumber, String message) {
        // TODO: Integrate with Twilio/Africa's Talking for production
        log.info("Sending SMS to {}: {}", phoneNumber, message);
    }
}