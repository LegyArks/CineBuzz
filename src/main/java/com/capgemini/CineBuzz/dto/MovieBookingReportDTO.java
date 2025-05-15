package com.capgemini.CineBuzz.dto;

public class MovieBookingReportDTO {
    private String movieName;
    private int totalSeats;

    public MovieBookingReportDTO(String movieName, int totalSeats) {
        this.movieName = movieName;
        this.totalSeats = totalSeats;
    }

	public MovieBookingReportDTO() {
		super();
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
    
	
	

    // getters & setters
}
