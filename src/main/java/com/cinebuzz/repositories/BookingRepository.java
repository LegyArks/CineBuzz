package com.cinebuzz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cinebuzz.dto.MovieRevenueReportDto;
import com.cinebuzz.entities.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUser_Name(String name);
	
	//adminDashCards
	
	@Query("SELECT SUM(b.seatsBooked * m.price) FROM Booking b JOIN b.showtime s JOIN s.movie m")
	Double getTotalRevenue();

    @Query("SELECT SUM(b.seatsBooked) FROM Booking b")
    Long getTotalTicketsSold();

    @Query("SELECT b.showtime.movie.title, SUM(b.seatsBooked) as total " +
           "FROM Booking b GROUP BY b.showtime.movie.title ORDER BY total DESC")
    List<Object[]> getMovieWatchCounts(); 
    
    @Query("SELECT new com.cinebuzz.dto.MovieRevenueReportDto(m.title, SUM(b.seatsBooked), SUM(b.seatsBooked * m.price)) " +
    	       "FROM Booking b JOIN b.showtime s JOIN s.movie m " +
    	       "GROUP BY m.title " +
    	       "ORDER BY SUM(b.seatsBooked * m.price) DESC")
    	List<MovieRevenueReportDto> getMovieRevenueReport();
    
  //adminDashCards
}