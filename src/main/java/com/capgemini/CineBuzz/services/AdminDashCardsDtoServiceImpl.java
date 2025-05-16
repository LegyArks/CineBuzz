package com.capgemini.CineBuzz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.dto.AdminDashCardsDto;
import com.capgemini.CineBuzz.repositories.BookingRepository;

@Service
public class AdminDashCardsDtoServiceImpl implements AdminDashCardsDtoService {
	
	private BookingRepository bookingRepo;

	public AdminDashCardsDtoServiceImpl(BookingRepository bookingRepo) {
		this.bookingRepo = bookingRepo;
	}

	@Override
	public AdminDashCardsDto getDashboardCards() {
		
		AdminDashCardsDto card = new AdminDashCardsDto();
		  
	    Double revenue = bookingRepo.getTotalRevenue();
	    card.setTotalRevenue(revenue != null ? revenue : 0.0);

	    Long ticketsSold = bookingRepo.getTotalTicketsSold();
	    card.setTotalTicketsSold(ticketsSold != null ? ticketsSold : 0);

	    List<Object[]> movieWatchCounts = bookingRepo.getMovieWatchCounts();
	    
	    if (!movieWatchCounts.isEmpty()) {
	        card.setMostWatchedMovie((String) movieWatchCounts.get(0)[0]); 
	        card.setLeastWatchedMovie((String) movieWatchCounts.get(movieWatchCounts.size() - 1)[0]);
	    } else {
	        card.setMostWatchedMovie("N/A");
	        card.setLeastWatchedMovie("N/A");
	    }

	    return card;
	}

}
