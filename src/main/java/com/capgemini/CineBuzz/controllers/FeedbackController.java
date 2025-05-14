package com.capgemini.CineBuzz.controllers;

import com.capgemini.CineBuzz.entities.Feedback;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.services.FeedbackService;
import com.capgemini.CineBuzz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final UserService userService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Long userId = feedback.getUser().getUserId();
        User user = userService.getUserById(userId);
        feedback.setUser(user);

        Feedback savedFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/feedbacks/" + savedFeedback.getFeedbackId()))
                .body(savedFeedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.status(HttpStatus.OK).body(feedbacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable("id") Long feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.status(HttpStatus.OK).body(feedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable("id") Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}