package com.capgemini.CineBuzz.services;

import java.util.List;

import com.capgemini.CineBuzz.entities.Booking;


public interface BookingService {
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    Booking createBooking(Booking booking);
    Booking updateBooking(Long id, Booking booking);
    Booking patchBooking(Long id, Booking booking);
    boolean deleteBooking(Long id);
    List<Booking> findByName(String name);
}
