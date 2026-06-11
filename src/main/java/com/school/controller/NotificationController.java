package com.school.controller;

import com.school.service.notification.EmailService;
import com.school.service.notification.PushNotificationService;
import com.school.service.notification.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
public class NotificationController {
    private final EmailService emailService;
    private final SmsService smsService;
    private final PushNotificationService pushNotificationService;

    public NotificationController(EmailService emailService, SmsService smsService, PushNotificationService pushNotificationService) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Map<String, String> payload) {
        String type = payload.get("type");
        String target = payload.get("target");
        String message = payload.get("message");
        String title = payload.getOrDefault("title", "School Notification");

        switch (type.toLowerCase()) {
            case "email":
                emailService.sendEmail(target, title, message);
                break;
            case "sms":
                smsService.sendSms(target, message);
                break;
            case "push":
                pushNotificationService.sendPushNotification(target, title, message);
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid notification type");
        }
        return ResponseEntity.ok("Notification sent successfully");
    }
}