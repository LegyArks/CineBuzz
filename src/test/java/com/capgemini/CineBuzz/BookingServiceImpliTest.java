package com.capgemini.CineBuzz;

import com.capgemini.CineBuzz.entities.Booking;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.entities.Showtime;
import com.capgemini.CineBuzz.exceptions.BookingNotFoundException;
import com.capgemini.CineBuzz.repositories.BookingRepository;
import com.capgemini.CineBuzz.services.BookingServiceImpli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImpliTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpli bookingService;

    private Booking booking;
    private User user;
    private Showtime showtime;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setName("Test User");

        showtime = new Showtime();
        showtime.setShowId(1L);

        booking = new Booking(1L, user, showtime, 2, LocalDate.now(), 500L);
    }

    @Test
    void testCreateBooking() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking savedBooking = bookingService.createBooking(booking);

        assertNotNull(savedBooking);
        assertEquals(2, savedBooking.getSeatsBooked());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllBookings();

        assertFalse(bookings.isEmpty());
        assertEquals(1, bookings.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking foundBooking = bookingService.getBookingById(1L);

        assertNotNull(foundBooking);
        assertEquals(2, foundBooking.getSeatsBooked());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookingByIdNotFound() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(999L));
        verify(bookingRepository, times(1)).findById(999L);
    }

    @Test
    void testUpdateBooking() {
        Booking updatedBooking = new Booking(1L, user, showtime, 3, LocalDate.now().plusDays(1), 600L);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(updatedBooking);

        Booking result = bookingService.updateBooking(1L, updatedBooking);

        assertNotNull(result);
        assertEquals(3, result.getSeatsBooked());
        assertEquals(600L, result.getAmount());
        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testPatchBooking() {
        Booking patch = new Booking();
        patch.setSeatsBooked(4);
        patch.setAmount(700L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.patchBooking(1L, patch);

        assertNotNull(result);
        assertEquals(4, result.getSeatsBooked());
        assertEquals(700L, result.getAmount());
        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testDeleteBooking() {
        when(bookingRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookingRepository).deleteById(1L);

        boolean deleted = bookingService.deleteBooking(1L);

        assertTrue(deleted);
        verify(bookingRepository, times(1)).existsById(1L);
        verify(bookingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookingNotFound() {
        when(bookingRepository.existsById(999L)).thenReturn(false);

        assertThrows(BookingNotFoundException.class, () -> bookingService.deleteBooking(999L));
        verify(bookingRepository, times(1)).existsById(999L);
    }

    @Test
    void testFindBookingsByUserName() {
        when(bookingRepository.findByUser_Name("Test User")).thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.findBookingsByUserName("Test User");

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        verify(bookingRepository, times(1)).findByUser_Name("Test User");
    }
}
