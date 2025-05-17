package com.cinebuzz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinebuzz.dto.BookingRequestDTO;
import com.cinebuzz.entities.Booking;
import com.cinebuzz.entities.Showtime;
import com.cinebuzz.entities.User;
import com.cinebuzz.exceptions.BookingNotFoundException;
import com.cinebuzz.exceptions.MovieNotFoundException;
import com.cinebuzz.exceptions.ShowtimeNotFoundException;
import com.cinebuzz.exceptions.UserNotFoundException;
import com.cinebuzz.repositories.BookingRepository;
import com.cinebuzz.repositories.MovieRepository;
import com.cinebuzz.repositories.ShowtimeRepository;
import com.cinebuzz.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingServiceImpli implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public BookingServiceImpli(BookingRepository bookingRepo ,UserRepository userRepo ,MovieRepository movieRepo , ShowtimeRepository showtimeRepo  ) {
        this.bookingRepository = bookingRepo;
        this.userRepository = userRepo;
        this.movieRepository = movieRepo;
        this.showtimeRepository = showtimeRepo;
    }

    @Override
    public List<Booking> getAllBookings() {
        log.info("Fetching all Bookings");
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        log.info("Fetching booking by ID: {}", id);
        return bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Booking not found with ID: {}", id);
                    return new BookingNotFoundException("Booking not found with id " + id);
                });
    }

    @Override
    public Booking createBooking(Booking booking) {
        log.info("Create new Booking for user: {}", booking.getUser());
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long id, Booking updatedBooking) {
        log.info("Updating booking with Id: {}", id);
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Booking not found with ID: {}", id);
                    return new BookingNotFoundException("Booking not found with id " + id);
                });
        existingBooking.setSeatsBooked(updatedBooking.getSeatsBooked());
        existingBooking.setBookingDate(updatedBooking.getBookingDate());
        existingBooking.setAmount(updatedBooking.getAmount());
        log.debug("Updating Booking details: {}", existingBooking);
        return bookingRepository.save(existingBooking);
    }

    @Override
    public Booking patchBooking(Long id, Booking patch) {
        log.info("Patching booking with Id: {}", id);
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Booking Id not found: {}", id);
                    return new BookingNotFoundException("Booking not found with id: " + id);
                });

        if (patch.getSeatsBooked() > 0) {
            existingBooking.setSeatsBooked(patch.getSeatsBooked());
        }
        if (patch.getBookingDate() != null) {
            existingBooking.setBookingDate(patch.getBookingDate());
        }
        if (patch.getAmount() != null) {
            existingBooking.setAmount(patch.getAmount());
        }
        log.debug("Patched Booking Details: {}", existingBooking);
        return bookingRepository.save(existingBooking);
    }

    @Override
    public boolean deleteBooking(Long id) {
        log.info("Deleting Booking Id: {}", id);
        if (!bookingRepository.existsById(id)) {
            log.warn("Booking Id not found: {}", id);
            throw new BookingNotFoundException("Booking not found with id " + id);
        }
        bookingRepository.deleteById(id);
        log.debug("Deleted Booking Id: {}", id);
        return true;
    }

    @Override
    public List<Booking> findBookingsByUserName(String name) {
        log.info("Fetching bookings for userName: {}", name);
        return bookingRepository.findByUser_Name(name);
    }
    @Override
    public Booking createBookingFromDTO(BookingRequestDTO dto) {
    	User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + dto.getUserId() + " not found"));

    	movieRepository.findById(dto.getMovieId())
        .orElseThrow(() -> new MovieNotFoundException("Movie with ID " + dto.getMovieId() + " not found"));


        Showtime showtime = showtimeRepository.findById(dto.getShowId())
                .orElseThrow(() -> new ShowtimeNotFoundException("Showtime with ID " + dto.getShowId() + " not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setSeatsBooked(dto.getSeatsBooked());
        booking.setBookingDate(dto.getBookingDate());
        booking.setAmount(dto.getAmount());

        return bookingRepository.save(booking);
    }


}
