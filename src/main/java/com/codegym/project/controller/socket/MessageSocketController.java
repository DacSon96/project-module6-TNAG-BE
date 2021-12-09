package com.codegym.project.controller.socket;

import com.codegym.project.helper.Timer;
import com.codegym.project.message.IMessageService;
import com.codegym.project.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@CrossOrigin("*")
public class MessageSocketController {
    @Autowired
    private IMessageService messageService;

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public Message sendMessage(Message message) {
        LocalDateTime time = Timer.getCurrentTime();
        message.setTime(time);
        Message newMessage = messageService.save(message);
        return newMessage;
    }
}
