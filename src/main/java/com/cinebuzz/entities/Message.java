package com.cinebuzz.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "message")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "name", "email", "password", "userType", "phoneNumber" })
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @NotBlank(message = "Name shoundn't be blank")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String message;

    private String adminResponse;

    @NotNull(message = "TimeStamp is required")
    @PastOrPresent(message = "TimeStamp cannot be in the future")
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

    public String getAdminResponse() {
        return adminResponse;
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse = adminResponse;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message [messageId=" + messageId + ", name=" + name + ", user="
                + (user != null ? user.getUserId() : null) + ", message=" + message
                + ", adminResponse=" + adminResponse
                + ", timestamp=" + timestamp + "]";
    }
}
