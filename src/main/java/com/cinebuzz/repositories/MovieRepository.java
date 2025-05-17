package com.cinebuzz.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebuzz.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
