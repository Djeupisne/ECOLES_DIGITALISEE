package com.school.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushNotificationService {
    public void sendPushNotification(String deviceToken, String title, String body) {
        // TODO: Integrate with Firebase Cloud Messaging (FCM) for production
        log.info("Sending Push Notification to device {}: {} - {}", deviceToken, title, body);
    }
}