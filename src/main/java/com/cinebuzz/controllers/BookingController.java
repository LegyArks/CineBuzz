package com.cinebuzz.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinebuzz.dto.BookingRequestDTO;
import com.cinebuzz.entities.Booking;
import com.cinebuzz.services.BookingService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("Fetching all bookings");
        List<Booking> bookings = bookingService.getAllBookings();
        log.debug("Fetched {} bookings", bookings.size());
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        log.info("Fetching booking with ID: {}", id);
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            log.debug("Found booking: {}", booking);
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        } else {
            log.warn("Booking not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    
    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking , BindingResult  bindingResult) {
        log.info("Creating new booking: {}", booking);
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Validation has failed");
		}

        Booking savedBooking = bookingService.createBooking(booking);
        log.debug("Booking created with ID: {}", savedBooking.getBookingId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/bookings/" + savedBooking.getBookingId()))
                .body(savedBooking);
    }

    @PutMapping("/{id}")

    public ResponseEntity<Booking> updateBooking(@PathVariable Long id,@Valid @RequestBody Booking booking , BindingResult  bindingResult) {
        log.info("Updating booking with ID: {}", id);
		    if (bindingResult.hasErrors()) {
			    throw new IllegalArgumentException("Validation has failed");
		    }

        Booking updatedBooking = bookingService.updateBooking(id, booking);
        log.debug("Updated booking: {}", updatedBooking);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        log.info("Deleting booking with ID: {}", id);
        boolean deleted = bookingService.deleteBooking(id);
        if (deleted) {
            log.debug("Deleted booking with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            log.warn("Failed to delete booking. Booking not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Booking>> getBookingsByName(@PathVariable("username") String username) {
        log.info("Fetching bookings for user: {}", username);
        List<Booking> bookings = bookingService.findBookingsByUserName(username);
        log.debug("Found {} bookings for user {}", bookings.size(), username);
        return ResponseEntity.ok(bookings);
    }
    
    @PostMapping("/bookingRequest")
    public ResponseEntity<Booking> simpleBooking(
            @Valid @RequestBody BookingRequestDTO bookingRequestDTO,
            BindingResult bindingResult) {

        log.info("Creating booking via simple DTO: {}", bookingRequestDTO);

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Validation failed for BookingRequestDTO");
        }

        Booking savedBooking = bookingService.createBookingFromDTO(bookingRequestDTO);
        log.debug("Booking created from DTO with ID: {}", savedBooking.getBookingId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/bookings/" + savedBooking.getBookingId()))
                .body(savedBooking);
    }
}
