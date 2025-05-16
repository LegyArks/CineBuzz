package com.capgemini.CineBuzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.CineBuzz.entities.Showtime;
import com.capgemini.CineBuzz.services.ShowtimeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @GetMapping
    public ResponseEntity<List<Showtime>> getAllShowtimes() {
        log.info("Fetching all showtimes");
        List<Showtime> showtimes = showtimeService.getAllShowtimes();
        log.info("Fetched {} showtimes", showtimes.size());
        return ResponseEntity.status(HttpStatus.OK).body(showtimes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtime(@PathVariable("id") Long showId) {
        log.info("Fetching showtime with ID: {}", showId);
        Showtime showtime = showtimeService.getShowtimeById(showId);
        if (showtime != null) {
            log.info("Showtime found: {}", showtime);
            return ResponseEntity.status(HttpStatus.OK).body(showtime);
        } else {
            log.warn("Showtime with ID {} not found", showId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping

    public ResponseEntity<Showtime> createShowtime(@Valid @RequestBody Showtime showtime, BindingResult  bindingResult) {
		log.info("Creating new showtime for movie ID: {}", showtime.getMovie().getId());
    if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}

        Showtime savedShowtime = showtimeService.createShowtime(showtime);
        log.info("Showtime created successfully with ID: {}", savedShowtime.getShowId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/showtimes/" + savedShowtime.getShowId()))
                .body(savedShowtime);
    }

    @PutMapping("/{id}")

    public ResponseEntity<Showtime> updateShowtime(@PathVariable("id") Long showId,@Valid @RequestBody Showtime showtime , BindingResult  bindingResult) {
		log.info("Updating showtime with ID: {}", showId);
    if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}

        Showtime updatedShowtime = showtimeService.updateShowtime(showId, showtime);
        log.info("Showtime updated successfully for ID: {}", showId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedShowtime);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Showtime> patchShowtime(@PathVariable("id") Long showId,@RequestBody Showtime showtime, BindingResult  bindingResult) {
		log.info("Patching showtime with ID: {}", showId);
    if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}

        Showtime patched = showtimeService.patchShowtime(showId, showtime);
        log.info("Showtime patched successfully for ID: {}", showId);
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable("id") Long showId) {
        log.info("Deleting showtime with ID: {}", showId);
        boolean deleted = showtimeService.deleteShowtime(showId);
        if (deleted) {
            log.info("Showtime with ID {} deleted successfully", showId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            log.warn("Failed to delete. Showtime with ID {} not found", showId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieId(@PathVariable Long movieId) {
        log.info("Fetching showtimes for movie ID: {}", movieId);
        List<Showtime> showtimes = showtimeService.findByMovieId(movieId);
        log.info("Found {} showtimes for movie ID: {}", showtimes.size(), movieId);
        return ResponseEntity.ok(showtimes);
    }
}
