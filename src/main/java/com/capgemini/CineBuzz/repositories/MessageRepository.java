package com.capgemini.CineBuzz.repositories;

import com.capgemini.CineBuzz.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}