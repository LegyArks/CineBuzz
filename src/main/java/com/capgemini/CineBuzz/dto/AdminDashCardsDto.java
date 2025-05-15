package com.capgemini.CineBuzz.dto;

public class AdminDashCardsDto {
	
    private String bestMovieByTickets;
    private String bestMovieByRevenue;
    private String worstMovieByTickets;
    private String worstMovieByRevenue;
    private Double totalRevenue;
    private String mostPopularGenre;
    private String mostPopularLanguage;
    
	public AdminDashCardsDto(String bestMovieByTickets, String bestMovieByRevenue, String worstMovieByTickets, String worstMovieByRevenue, Double totalRevenue, String mostPopularGenre, String mostPopularLanguage) {
		
		this.bestMovieByTickets = bestMovieByTickets;
		this.bestMovieByRevenue = bestMovieByRevenue;
		this.worstMovieByTickets = worstMovieByTickets;
		this.worstMovieByRevenue = worstMovieByRevenue;
		this.totalRevenue = totalRevenue;
		this.mostPopularGenre = mostPopularGenre;
		this.mostPopularLanguage = mostPopularLanguage;
	}

	public AdminDashCardsDto() {

	}

	public String getBestMovieByTickets() {
		return bestMovieByTickets;
	}

	public void setBestMovieByTickets(String bestMovieByTickets) {
		this.bestMovieByTickets = bestMovieByTickets;
	}

	public String getBestMovieByRevenue() {
		return bestMovieByRevenue;
	}

	public void setBestMovieByRevenue(String bestMovieByRevenue) {
		this.bestMovieByRevenue = bestMovieByRevenue;
	}

	public String getWorstMovieByTickets() {
		return worstMovieByTickets;
	}

	public void setWorstMovieByTickets(String worstMovieByTickets) {
		this.worstMovieByTickets = worstMovieByTickets;
	}

	public String getWorstMovieByRevenue() {
		return worstMovieByRevenue;
	}

	public void setWorstMovieByRevenue(String worstMovieByRevenue) {
		this.worstMovieByRevenue = worstMovieByRevenue;
	}

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public String getMostPopularGenre() {
		return mostPopularGenre;
	}

	public void setMostPopularGenre(String mostPopularGenre) {
		this.mostPopularGenre = mostPopularGenre;
	}

	public String getMostPopularLanguage() {
		return mostPopularLanguage;
	}

	public void setMostPopularLanguage(String mostPopularLanguage) {
		this.mostPopularLanguage = mostPopularLanguage;
	}

	@Override
	public String toString() {
		return "AdminDashCardsDto [bestMovieByTickets=" + bestMovieByTickets + ", bestMovieByRevenue="
				+ bestMovieByRevenue + ", worstMovieByTickets=" + worstMovieByTickets + ", worstMovieByRevenue="
				+ worstMovieByRevenue + ", totalRevenue=" + totalRevenue + ", mostPopularGenre=" + mostPopularGenre
				+ ", mostPopularLanguage=" + mostPopularLanguage + "]";
	}
	
	
	
	
    
    
    
    
}
