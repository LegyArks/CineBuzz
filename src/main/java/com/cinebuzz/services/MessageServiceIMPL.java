package com.cinebuzz.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinebuzz.entities.Message;
import com.cinebuzz.exceptions.MessageNotFoundException;
import com.cinebuzz.repositories.MessageRepository;

import java.util.List;

@Service
@Slf4j
public class MessageServiceIMPL implements MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceIMPL(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message createMessage(Message message) {
        log.info("Creating Message: {}", message);
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        log.info("Fetching all messages");
        return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(Long id) {
        log.info("Fetching message by Id: {}", id);
        return messageRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Message not found with ID: {}", id);
                    return new MessageNotFoundException("Message not found with id: " + id);
                });
    }

    @Override
    public void deleteMessage(Long id) {
        log.info("Delete message with ID: {}", id);
        if (!messageRepository.existsById(id)) {
            log.warn("Message not found with ID: {}", id);
            throw new MessageNotFoundException("Message not found with id: " + id);
        }
        messageRepository.deleteById(id);
        log.info("Message deleted with ID: {}", id);
    }

    // --- Add this for admin response ---
    @Override
    public Message updateAdminResponse(Long id, String adminResponse) {
        log.info("Updating admin response for message ID: {}", id);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Message not found with ID: {}", id);
                    return new MessageNotFoundException("Message not found with id: " + id);
                });
        message.setAdminResponse(adminResponse);
        Message updatedMessage = messageRepository.save(message);
        log.info("Admin response updated for message ID: {}", id);
        return updatedMessage;
    }
}
