package com.cinebuzz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebuzz.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}