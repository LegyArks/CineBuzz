package com.capgemini.CineBuzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.CineBuzz.entities.Movie;
import com.capgemini.CineBuzz.services.MovieService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

@Slf4j
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
        log.info("Fetching all movies");
        List<Movie> movies = movieService.getAllMovies();
        log.info("Fetched movies count: {}", movies.size());
        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long movieId) {
        log.info("Fetching movie with ID: {}", movieId);
        Movie movie = movieService.getMovieById(movieId);
        if (movie != null) {
            log.info("Fetched movie: {}", movie.getTitle());
            return ResponseEntity.status(HttpStatus.OK).body(movie);
        } else {
            log.warn("Movie with ID {} not found", movieId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie , BindingResult  bindingResult) {
		  log.info("Creating new movie: {}", movie.getTitle());
      if (bindingResult.hasErrors()) {
			  throw new IllegalArgumentException("Validation has failed");
		   }

        Movie savedMovie = movieService.createMovie(movie);
        log.info("Movie created successfully with ID: {}", savedMovie.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/movies/" + savedMovie.getId()))
                .body(savedMovie);
    }

    @PutMapping("/{movieId}")

    public ResponseEntity<Movie> updateMovie(@PathVariable Long movieId,@Valid @RequestBody Movie movie ,  BindingResult  bindingResult) {
		log.info("Updating movie with ID: {}", movieId);
    if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}
        Movie updatedMovie = movieService.updateMovie(movieId, movie);
        log.info("Movie updated successfully: {}", updatedMovie.getTitle());
        return ResponseEntity.status(HttpStatus.OK).body(updatedMovie);
    }

    @PatchMapping("/{movieId}")

    public ResponseEntity<Movie> patchMovie(@PathVariable Long movieId,@Valid @RequestBody Movie movie, BindingResult  bindingResult) {
		  log.info("Patching movie with ID: {}", movieId);
    if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}

        Movie patchedMovie = movieService.patchMovie(movieId, movie);
        log.info("Movie patched successfully: {}", patchedMovie.getTitle());
        return ResponseEntity.ok(patchedMovie);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        log.info("Deleting movie with ID: {}", movieId);
        boolean deleted = movieService.deleteMovie(movieId);
        if (deleted) {
            log.info("Movie with ID {} deleted successfully", movieId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            log.warn("Failed to delete movie. Movie with ID {} not found", movieId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
