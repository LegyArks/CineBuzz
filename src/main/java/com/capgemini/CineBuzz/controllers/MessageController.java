package com.capgemini.CineBuzz.controllers;

import com.capgemini.CineBuzz.entities.Message;
import com.capgemini.CineBuzz.services.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
//    	Long userId = message.getUser().getId();
//        User user = userService.getUserById(userId);
//        message.setUser(user);
    	
    	Message savedMessage = messageService.createMessage(message);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/messages/" + savedMessage.getMessageId()))
                .body(savedMessage);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}