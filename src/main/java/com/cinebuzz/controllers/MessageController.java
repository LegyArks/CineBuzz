package com.cinebuzz.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cinebuzz.entities.Message;
import com.cinebuzz.services.MessageService;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

    // -------------------- NEW: Update admin response --------------------
    @PutMapping("/{id}/admin-response")
    public ResponseEntity<Message> updateAdminResponse(
            @PathVariable("id") Long messageId,
            @RequestBody Map<String, String> requestBody) {
        log.info("Updating admin response for message ID: {}", messageId);

        String adminResponse = requestBody.get("adminResponse");
        if (adminResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        Message updated = messageService.updateAdminResponse(messageId, adminResponse);
        log.info("Admin response updated for message ID: {}", messageId);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
