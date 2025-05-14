package com.capgemini.CineBuzz.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String feedback;

    private LocalDateTime timeStamp;

    public Feedback() {
        this.timeStamp = LocalDateTime.now();
    }

    public Feedback(Long feedbackId, String name, User user, String feedback, LocalDateTime timeStamp) {
        this.feedbackId = feedbackId;
        this.name = name;
        this.user = user;
        this.feedback = feedback;
        this.timeStamp = timeStamp;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getTimestamp() {
        return timeStamp;
    }

    public void setTimestamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Feedback [feedbackId=" + feedbackId + ", name=" + name + ", user=" + (user != null ? user.getUserId() : null) + ", feedback=" + feedback + ", timeStamp=" + timeStamp + "]";
    }
}