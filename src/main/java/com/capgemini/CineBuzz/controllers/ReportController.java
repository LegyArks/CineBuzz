package com.capgemini.CineBuzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.CineBuzz.dto.DateRevenueReportDTO;
import com.capgemini.CineBuzz.dto.MovieBookingReportDTO;
import com.capgemini.CineBuzz.entities.Booking;
import com.capgemini.CineBuzz.repositories.BookingRepository;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private BookingRepository bookingRepo;
    
    @Autowired
    public ReportController(BookingRepository bookingRepo) {
		super();
		this.bookingRepo = bookingRepo;
	}

    @GetMapping("/movie-bookings")
    public List<MovieBookingReportDTO> getMovieWiseBookings() {
        List<Booking> bookings = bookingRepo.findAll();
        Map<String, Integer> movieSeatMap = new HashMap<>();

        for (Booking booking : bookings) {
            if (booking.getShowtime() != null && booking.getShowtime().getMovie() != null) {
                String movieName = booking.getShowtime().getMovie().getTitle(); 
                movieSeatMap.put(movieName,
                        movieSeatMap.getOrDefault(movieName, 0) + booking.getSeatsBooked());
            }
        }

        List<MovieBookingReportDTO> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : movieSeatMap.entrySet()) {
            result.add(new MovieBookingReportDTO(entry.getKey(), entry.getValue()));
        }

        return result;
    }

    @GetMapping("/date-revenue")
    public List<DateRevenueReportDTO> getDateWiseRevenue() {
        List<Booking> bookings = bookingRepo.findAll();
        Map<LocalDate, DateRevenueReportDTO> dateMap = new TreeMap<>();

        for (Booking booking : bookings) {
            LocalDate date = booking.getBookingDate();
            dateMap.putIfAbsent(date, new DateRevenueReportDTO(date, 0.0, 0));
            DateRevenueReportDTO report = dateMap.get(date);
            report.setTotalRevenue(report.getTotalRevenue() + booking.getAmount());
            report.setTotalTickets(report.getTotalTickets() + booking.getSeatsBooked());
        }

        return new ArrayList<>(dateMap.values());
    }
}

