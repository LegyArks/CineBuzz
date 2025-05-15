package com.capgemini.CineBuzz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.CineBuzz.entities.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUser_Name(String name);
	
	//AdminDashCards Starts here
	@Query("SELECT b.showtime.movie.title, SUM(b.noOfTickets) " +
			"FROM Booking b GROUP BY b.showtime.movie.title ORDER BY SUM(b.noOfTickets) DESC")
	List<Object[]> getMoviesByTicketCount();
	
	@Query("SELECT b.showtime.movie.title, SUM(b.noOfTickets * b.showtime.price) " +
	         "FROM Booking b GROUP BY b.showtime.movie.title ORDER BY SUM(b.noOfTickets * b.showtime.price) DESC")
	List<Object[]> getMoviesByRevenue();

	@Query("SELECT SUM(b.noOfTickets * b.showtime.price) FROM Booking b")
	Double getTotalRevenue();

	@Query("SELECT b.showtime.movie.genre, SUM(b.noOfTickets) " +
			"FROM Booking b GROUP BY b.showtime.movie.genre ORDER BY SUM(b.noOfTickets) DESC")
	List<Object[]> getGenrePopularity();

	@Query("SELECT b.showtime.movie.language, SUM(b.noOfTickets) " +
			"FROM Booking b GROUP BY b.showtime.movie.language ORDER BY SUM(b.noOfTickets) DESC")
	List<Object[]> getLanguagePopularity();
    
	//AdminDashCards Ends here

}