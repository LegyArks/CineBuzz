package com.capgemini.CineBuzz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.CineBuzz.entities.Showtime;

import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime,Long> {
    List<Showtime> findByMovieId(Long movieId);
}
