package com.capgemini.CineBuzz.services;

import com.capgemini.CineBuzz.entities.Message;
import com.capgemini.CineBuzz.exceptions.MessageNotFoundException;
import com.capgemini.CineBuzz.repositories.MessageRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
