package com.capgemini.CineBuzz.services;

import com.capgemini.CineBuzz.entities.Message;
import com.capgemini.CineBuzz.exceptions.MessageNotFoundException;
import com.capgemini.CineBuzz.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceIMPL implements MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceIMPL(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message createMessage(Message message) {
        // Assumes the controller has already set the User association correctly
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found with id: " + messageId));
    }

    @Override
    public void deleteMessage(Long messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new MessageNotFoundException("Message not found with id: " + messageId);
        }
        messageRepository.deleteById(messageId);
    }
}
