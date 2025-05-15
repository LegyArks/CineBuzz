package com.capgemini.CineBuzz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.dto.AdminDashCardsDto;
import com.capgemini.CineBuzz.repositories.BookingRepository;

@Service
public class AdminDashCardsDtoServiceImpl implements AdminDashCardsDtoService {
	
	private BookingRepository bookingRepo;
	
	@Autowired
    public AdminDashCardsDtoServiceImpl(BookingRepository bookingRepo) {
		this.bookingRepo = bookingRepo;
	}

	@Override
    public AdminDashCardsDto getDashboardCards() {
    	AdminDashCardsDto dto = new AdminDashCardsDto();

        List<Object[]> byTickets = bookingRepo.getMoviesByTicketCount();
        List<Object[]> byRevenue = bookingRepo.getMoviesByRevenue();
        List<Object[]> genrePop = bookingRepo.getGenrePopularity();
        List<Object[]> langPop = bookingRepo.getLanguagePopularity();

        dto.setBestMovieByTickets((String) byTickets.get(0)[0]);
        dto.setWorstMovieByTickets((String) byTickets.get(byTickets.size() - 1)[0]);

        dto.setBestMovieByRevenue((String) byRevenue.get(0)[0]);
        dto.setWorstMovieByRevenue((String) byRevenue.get(byRevenue.size() - 1)[0]);

        dto.setTotalRevenue(bookingRepo.getTotalRevenue());

        dto.setMostPopularGenre((String) genrePop.get(0)[0]);
        dto.setMostPopularLanguage((String) langPop.get(0)[0]);

        return dto;
    }

}
