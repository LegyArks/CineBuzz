package com.capgemini.CineBuzz.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.apache.catalina.User;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String message;

    private LocalDateTime timestamp;

    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    public Message(Long messageId, String name, User user, String message, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.name = name;
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    

    public Message(Long messageId, String name, String message, LocalDateTime timestamp) {
		super();
		this.messageId = messageId;
		this.name = name;
		this.message = message;
		this.timestamp = timestamp;
	}

	

    public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message [messageId=" + messageId + ", name=" + name + ", user=" + (user != null ? user.getId() : null) + ", message=" + message + ", timestamp=" + timestamp + "]";
    }
}
