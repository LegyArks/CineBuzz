package com.capgemini.CineBuzz.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.CineBuzz.entities.Booking;
import com.capgemini.CineBuzz.services.BookingService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking , BindingResult  bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}
        Booking savedBooking = bookingService.createBooking(booking);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/bookings/" + savedBooking.getBookingId()))
                .body(savedBooking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id,@Valid @RequestBody Booking booking , BindingResult  bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}
        Booking updatedBooking = bookingService.updateBooking(id, booking);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        boolean deleted = bookingService.deleteBooking(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<List<Booking>> getBookingsByName(@PathVariable String name) {
        List<Booking> bookings = bookingService.findBookingsByUserName(name);
        return ResponseEntity.ok(bookings);
    }
}

