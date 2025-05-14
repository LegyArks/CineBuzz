package com.capgemini.CineBuzz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.entities.Booking;
import com.capgemini.CineBuzz.exceptions.BookingNotFoundException;
import com.capgemini.CineBuzz.repositories.BookingRepository;


@Service
public class BookingServiceImpli implements BookingService{
	private final BookingRepository bookingRepository;
	
	@Autowired
	public BookingServiceImpli(BookingRepository bookingRepo) {
		this.bookingRepository = bookingRepo;
	}
	
	 @Override
	    public List<Booking> getAllBookings() {
	        return bookingRepository.findAll();
	    }

	    @Override
	    public Booking getBookingById(Long id) {
	        return bookingRepository.findById(id)
	                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id " + id));
	    }

	    @Override
	    public Booking createBooking(Booking booking) {
	        return bookingRepository.save(booking);
	    }

	    @Override
	    public Booking updateBooking(Long id, Booking updatedBooking) {
	        Booking existingBooking = bookingRepository.findById(id)
	                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id " + id));
	        existingBooking.setSeatsBooked(updatedBooking.getSeatsBooked());
	        existingBooking.setBookingDate(updatedBooking.getBookingDate());
	        existingBooking.setAmount(updatedBooking.getAmount());

	        return bookingRepository.save(existingBooking);
	    }

	    @Override
	    public Booking patchBooking(Long id, Booking patch) {
	        Booking existingBooking = bookingRepository.findById(id)
	                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));

	        if (patch.getSeatsBooked() > 0) {
	            existingBooking.setSeatsBooked(patch.getSeatsBooked());
	        }
	        if (patch.getBookingDate() != null) {
	            existingBooking.setBookingDate(patch.getBookingDate());
	        }
	        if (patch.getAmount() != null) {
	            existingBooking.setAmount(patch.getAmount());
	        }

	        return bookingRepository.save(existingBooking);
	    }


	    @Override
	    public boolean deleteBooking(Long id) {
	        if (!bookingRepository.existsById(id)) {
	            throw new BookingNotFoundException("Booking not found with id " + id);
	        }
	        bookingRepository.deleteById(id);
	        return true;
	    }

	    @Override
	    public List<Booking> findByName(String name) {
	        return bookingRepository.findByName(name);
	    }

}

