package com.codegym.project.controller.socket;

import com.codegym.project.helper.Timer;
import com.codegym.project.notification.INotificationService;
import com.codegym.project.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@CrossOrigin("*")
public class NotificationSocketController {
    @Autowired
    private INotificationService notificationService;

    @MessageMapping("/notify")
    @SendTo("/topic/notify")
    public Notification newNotify(Notification notification) {
        LocalDateTime now = Timer.getCurrentTime();
        notification.setTime(now);
        Notification newNotify = notificationService.save(notification);
        return newNotify;
    }
}
