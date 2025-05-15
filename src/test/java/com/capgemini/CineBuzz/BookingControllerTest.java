package com.capgemini.CineBuzz;

import com.capgemini.CineBuzz.controllers.BookingController;
import com.capgemini.CineBuzz.entities.Booking;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setUserId(1L);
        user.setName("Test User");

        booking = new Booking();
        booking.setBookingId(1L);
        booking.setUser(user);
        booking.setSeatsBooked(2);
        booking.setBookingDate(LocalDate.now());
        booking.setAmount(500L);
    }

    @Test
    void testGetAllBookings() {
        when(bookingService.getAllBookings()).thenReturn(Collections.singletonList(booking));

        ResponseEntity<List<Booking>> response = bookingController.getAllBookings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void testGetBookingFound() {
        when(bookingService.getBookingById(1L)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.getBooking(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(booking.getBookingId(), response.getBody().getBookingId());
        verify(bookingService, times(1)).getBookingById(1L);
    }

    @Test
    void testGetBookingNotFound() {
        when(bookingService.getBookingById(2L)).thenReturn(null);

        ResponseEntity<Booking> response = bookingController.getBooking(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookingService, times(1)).getBookingById(2L);
    }

    @Test
    void testCreateBooking() {
        when(bookingService.createBooking(any(Booking.class))).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.createBooking(booking, null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(booking.getBookingId(), response.getBody().getBookingId());
        verify(bookingService, times(1)).createBooking(any(Booking.class));
    }

    @Test
    void testUpdateBooking() {
        when(bookingService.updateBooking(eq(1L), any(Booking.class))).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.updateBooking(1L, booking, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(booking.getBookingId(), response.getBody().getBookingId());
        verify(bookingService, times(1)).updateBooking(eq(1L), any(Booking.class));
    }

    @Test
    void testDeleteBookingSuccess() {
        when(bookingService.deleteBooking(1L)).thenReturn(true);

        ResponseEntity<Void> response = bookingController.deleteBooking(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookingService, times(1)).deleteBooking(1L);
    }

    @Test
    void testDeleteBookingNotFound() {
        when(bookingService.deleteBooking(2L)).thenReturn(false);

        ResponseEntity<Void> response = bookingController.deleteBooking(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookingService, times(1)).deleteBooking(2L);
    }

    @Test
    void testGetBookingsByName() {
        when(bookingService.findBookingsByUserName("Test User")).thenReturn(Collections.singletonList(booking));

        ResponseEntity<List<Booking>> response = bookingController.getBookingsByName("Test User");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(bookingService, times(1)).findBookingsByUserName("Test User");
    }
}
