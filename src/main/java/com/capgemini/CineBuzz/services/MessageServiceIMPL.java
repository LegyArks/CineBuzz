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
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message not found with id: " + id));
    }

    @Override
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new MessageNotFoundException("Message not found with id: " + id);
        }
        messageRepository.deleteById(id);
    }
}
