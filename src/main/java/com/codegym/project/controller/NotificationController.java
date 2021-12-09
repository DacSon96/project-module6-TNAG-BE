package com.codegym.project.controller;

import com.codegym.project.notification.INotificationService;
import com.codegym.project.notification.Notification;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/notify")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Iterable> findAllNotificationsByReceiver(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Notification> notifications = notificationService.findAllByReceiverOrderByTimeDesc(optionalUser.get());
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

}
