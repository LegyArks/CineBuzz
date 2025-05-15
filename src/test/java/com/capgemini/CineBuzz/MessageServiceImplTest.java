package com.capgemini.CineBuzz;

import com.capgemini.CineBuzz.entities.Message;
import com.capgemini.CineBuzz.exceptions.MessageNotFoundException;
import com.capgemini.CineBuzz.repositories.MessageRepository;
import com.capgemini.CineBuzz.services.MessageServiceIMPL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceIMPL messageService;

    private Message message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        message = new Message(1L, "Test User", "Hello!", LocalDateTime.now());
    }

    @Test
    void testCreateMessage() {
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        Message savedMessage = messageService.createMessage(message);

        assertNotNull(savedMessage);
        assertEquals("Test User", savedMessage.getName());
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void testGetAllMessages() {
        when(messageRepository.findAll()).thenReturn(List.of(message));

        List<Message> messages = messageService.getAllMessages();

        assertFalse(messages.isEmpty());
        assertEquals(1, messages.size());
        verify(messageRepository, times(1)).findAll();
    }

    @Test
    void testGetMessageById() {
        when(messageRepository.findById(anyLong())).thenReturn(Optional.of(message));

        Message foundMessage = messageService.getMessageById(1L);

        assertNotNull(foundMessage);
        assertEquals("Test User", foundMessage.getName());
        verify(messageRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMessageByIdNotFound() {
        when(messageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MessageNotFoundException.class, () -> messageService.getMessageById(999L));

        verify(messageRepository, times(1)).findById(999L);
    }

    @Test
    void testDeleteMessage() {
        when(messageRepository.existsById(anyLong())).thenReturn(true);

        messageService.deleteMessage(1L);

        verify(messageRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMessageNotFound() {
        when(messageRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(MessageNotFoundException.class, () -> messageService.deleteMessage(999L));

        verify(messageRepository, times(1)).existsById(999L);
    }
}
