package com.capgemini.CineBuzz.services;

import com.capgemini.CineBuzz.entities.Feedback;
import java.util.List;

public interface FeedbackService {
    Feedback createFeedback(Feedback feedback);
    List<Feedback> getAllFeedbacks();
    Feedback getFeedbackById(Long feedbackId);
    void deleteFeedback(Long feedbackId);
}