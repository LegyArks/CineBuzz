package com.capgemini.CineBuzz.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.CineBuzz.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
