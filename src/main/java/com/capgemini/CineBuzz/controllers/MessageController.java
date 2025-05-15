package com.capgemini.CineBuzz.controllers;

import com.capgemini.CineBuzz.entities.Message;
import com.capgemini.CineBuzz.services.MessageService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@Valid @RequestBody Message message ,  BindingResult  bindingResult) {

        log.info("Creating new message from user");
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}
    	Message savedMessage = messageService.createMessage(message);
      log.info("Message created successfully with ID : {} ", savedMessage.getMessageId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/messages/" + savedMessage.getMessageId()))
                .body(savedMessage);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        log.info("Fetching all messages");

        List<Message> messages = messageService.getAllMessages();

        log.info("Total messages found: {}", messages.size());

        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable("id") Long messageId) {
        log.info("Fetching message with ID: {}", messageId);

        Message message = messageService.getMessageById(messageId);

        log.info("Fetched message: {}", message);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("id") Long messageId) {
        log.info("Deleting message with ID: {}", messageId);

        messageService.deleteMessage(messageId);

        log.info("Message with ID {} deleted successfully", messageId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}