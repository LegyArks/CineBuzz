package com.capgemini.CineBuzz.services;

import com.capgemini.CineBuzz.entities.Message;
import java.util.List;

public interface MessageService {
    Message createMessage(Message message);
    List<Message> getAllMessages();
    Message getMessageById(Long messageId);
    void deleteMessage(Long messageId);
}
