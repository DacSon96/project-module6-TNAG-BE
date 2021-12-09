package com.codegym.project.controller;

import com.codegym.project.message.IMessageService;
import com.codegym.project.message.Message;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private IMessageService messageService;

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Iterable<Message>> getMessagesBySenderAndReceiver(Authentication authentication, @PathVariable Long id, Pageable pageable) {
        User sender = userService.getUserFromAuthentication(authentication);
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            User receiver = optionalUser.get();
            Iterable<Message> messages = messageService.getAllHistoryChat(sender.getId(), receiver.getId(), 10);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
    }
}
