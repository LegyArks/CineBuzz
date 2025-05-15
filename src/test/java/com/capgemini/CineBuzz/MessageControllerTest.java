package com.capgemini.CineBuzz;

import com.capgemini.CineBuzz.controllers.MessageController;
import com.capgemini.CineBuzz.entities.Message;
import com.capgemini.CineBuzz.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    private Message message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
        message = new Message(1L, "Test User", "Hello!", LocalDateTime.now());
    }
    @Test
    void testCreateMessage() {
    	BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);
    	
        when(messageService.createMessage(any(Message.class))).thenReturn(message);

        ResponseEntity<Message> response = messageController.createMessage(message, mockBindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test User", response.getBody().getName());
        assertNotNull(response.getBody().getMessageId());
        verify(messageService, times(1)).createMessage(any(Message.class));
    }

    @Test
    void testGetAllMessages() {
        when(messageService.getAllMessages()).thenReturn(List.of(message));

        ResponseEntity<List<Message>> response = messageController.getAllMessages();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(messageService, times(1)).getAllMessages();
    }

    @Test
    void testGetMessageById() {
        when(messageService.getMessageById(anyLong())).thenReturn(message);

        ResponseEntity<Message> response = messageController.getMessage(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test User", response.getBody().getName());
        verify(messageService, times(1)).getMessageById(1L);
    }

    @Test
    void testDeleteMessage() {
        doNothing().when(messageService).deleteMessage(1L);

        ResponseEntity<Void> response = messageController.deleteMessage(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(messageService, times(1)).deleteMessage(1L);
    }
}
