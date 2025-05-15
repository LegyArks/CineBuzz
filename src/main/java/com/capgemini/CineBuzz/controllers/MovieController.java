package com.capgemini.CineBuzz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.CineBuzz.entities.Movie;
import com.capgemini.CineBuzz.services.MovieService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        return ResponseEntity.status(HttpStatus.OK).body(movie);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie , BindingResult  bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}
        Movie savedMovie = movieService.createMovie(movie);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/movies/" + savedMovie.getId()))
                .body(savedMovie);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long movieId,@Valid @RequestBody Movie movie ,  BindingResult  bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}
        Movie updatedMovie = movieService.updateMovie(movieId, movie);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMovie);
    }

    @PatchMapping("/{movieId}")
    public ResponseEntity<Movie> patchMovie(@PathVariable Long movieId,@Valid @RequestBody Movie movie, BindingResult  bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}
        Movie patchedMovie = movieService.patchMovie(movieId, movie);
        return ResponseEntity.ok(patchedMovie);
    }

    //Just a comment
    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        boolean deleted = movieService.deleteMovie(movieId);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
