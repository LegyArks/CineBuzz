package com.cinebuzz.services;

import java.util.List;

import com.cinebuzz.dto.BookingRequestDTO;
import com.cinebuzz.entities.Booking;


public interface BookingService {
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    Booking createBooking(Booking booking);
    Booking updateBooking(Long id, Booking booking);
    Booking patchBooking(Long id, Booking booking);
    boolean deleteBooking(Long id);

    List<Booking> findBookingsByUserName(String name);
    Booking createBookingFromDTO(BookingRequestDTO dto);

}
