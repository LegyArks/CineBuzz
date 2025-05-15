package com.capgemini.CineBuzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.CineBuzz.entities.Showtime;
import com.capgemini.CineBuzz.services.ShowtimeService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

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
        List<Showtime> showtimes = showtimeService.getAllShowtimes();
        return ResponseEntity.status(HttpStatus.OK).body(showtimes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtime(@PathVariable  Long showId) {
        Showtime showtime = showtimeService.getShowtimeById(showId);
        if (showtime != null) {
            return ResponseEntity.status(HttpStatus.OK).body(showtime);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Showtime> createShowtime(@Valid @RequestBody Showtime showtime) {
        Showtime savedShowtime = showtimeService.createShowtime(showtime);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/showtimes/" + savedShowtime.getShowId()))
                .body(savedShowtime);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long showId,@Valid @RequestBody Showtime showtime) {
        Showtime updatedShowtime = showtimeService.updateShowtime(showId, showtime);
        return ResponseEntity.status(HttpStatus.OK).body(updatedShowtime);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Showtime> patchShowtime(@PathVariable Long showId,@Valid @RequestBody Showtime showtime) {
        Showtime patched = showtimeService.patchShowtime(showId, showtime);
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showId) {
        boolean deleted = showtimeService.deleteShowtime(showId);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieId(@PathVariable Long movieId) {
        List<Showtime> showtimes = showtimeService.findByMovieId(movieId);
        return ResponseEntity.ok(showtimes);
    }
}