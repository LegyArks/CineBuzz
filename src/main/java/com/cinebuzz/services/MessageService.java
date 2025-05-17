package com.cinebuzz.services;

import java.util.List;

import com.cinebuzz.entities.Message;

public interface MessageService {
    Message createMessage(Message message);
    List<Message> getAllMessages();
    Message getMessageById(Long id);
    void deleteMessage(Long id);

    // --- Add this for admin response ---
    Message updateAdminResponse(Long id, String adminResponse);
}
