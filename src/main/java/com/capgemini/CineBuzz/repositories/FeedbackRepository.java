package com.capgemini.CineBuzz.repositories;

import com.capgemini.CineBuzz.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}