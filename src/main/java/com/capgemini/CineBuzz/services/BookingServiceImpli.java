package com.capgemini.CineBuzz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.entities.Booking;
import com.capgemini.CineBuzz.exceptions.BookingNotFoundException;
import com.capgemini.CineBuzz.repositories.BookingRepository;
import lombok.extern.slf4j.Slf4j; 

@Service
@Slf4j

public class BookingServiceImpli implements BookingService{
	private final BookingRepository bookingRepository;
	
	@Autowired
	public BookingServiceImpli(BookingRepository bookingRepo) {
		this.bookingRepository = bookingRepo;
	}
	
	 @Override
	    public List<Booking> getAllBookings() {
		 log.info("Fetching all Bookings");
	        return bookingRepository.findAll();
	    }

	    @Override
	    public Booking getBookingById(Long id) {
	    	log.info("Fetching booking by ID: ", id);
	        return bookingRepository.findById(id)
	                .orElseThrow(() ->{
	                	log.warn("Booking not found with ID: {}", id);
	                	return new BookingNotFoundException("Booking not found with id " + id);
	                });
	    }

	    @Override
	    public Booking createBooking(Booking booking) {
	    	log.info("Create new Booking for user : " + booking.getUser());
	        return bookingRepository.save(booking);
	    }

	    @Override
	    public Booking updateBooking(Long id, Booking updatedBooking) {
	    	log.info("Updating booking with Id :  " , id);
	        Booking existingBooking = bookingRepository.findById(id)
	                .orElseThrow(() -> {
	                    log.warn("Booking not found with ID:", id);
	                    return new BookingNotFoundException("Booking not found with id " + id);
	                });
	        existingBooking.setSeatsBooked(updatedBooking.getSeatsBooked());
	        existingBooking.setBookingDate(updatedBooking.getBookingDate());
	        existingBooking.setAmount(updatedBooking.getAmount());
	        log.debug("Updating Booking details" , existingBooking);
	        return bookingRepository.save(existingBooking);
	    }

	    @Override
	    public Booking patchBooking(Long id, Booking patch) {
	    	log.info("Patching booking with Id : "  , id);
	        Booking existingBooking = bookingRepository.findById(id)
	                .orElseThrow(() ->{ 
	                	log.warn("Booking Id not found " , id);
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
	        log.debug("	Patched Booking Details" , existingBooking);
	        return bookingRepository.save(existingBooking);
	    }


	    @Override
	    public boolean deleteBooking(Long id) {
	    	log.info("Deleting Booking Id : " , id);
	        if (!bookingRepository.existsById(id)) {
	        	log.warn("Booking Id not found : " , id);
	            throw new BookingNotFoundException("Booking not found with id " + id);
	        }
	        bookingRepository.deleteById(id);
	        log.debug("Deleted Booking Id : " , id);
	        return true;
	    }

	    @Override
	    public List<Booking> findBookingsByUserName(String name) {
	    	log.info("Fetching bookings for userName : " , name);
	        return bookingRepository.findByUser_Name(name);
	    }
}

