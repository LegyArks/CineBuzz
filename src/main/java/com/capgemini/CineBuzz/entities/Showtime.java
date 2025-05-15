package com.capgemini.CineBuzz.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "showtime")
public class Showtime {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long showId;
	@ManyToOne
	@JoinColumn(name="movieId", referencedColumnName="movieId")
	private Movie movie;
	
	@NotNull(message = "Show date is required")
    @FutureOrPresent(message = "Show date cannot be in the past")
	private LocalDate showDate;
	
	 @NotNull(message = "Show time is required")
	private LocalTime showTime;
	
	@PositiveOrZero(message = "Available seats cannot be negative")
	private int availableSeats;

	public Showtime() {
		
	}

	public Showtime(Long showId, LocalDate showDate, LocalTime showTime, int availableSeats) {
		super();
		this.showId = showId;
		this.showDate = showDate;
		this.showTime = showTime;
		this.availableSeats = availableSeats;
	}



	public Showtime(Long showId, Movie movie, LocalDate showDate, LocalTime showTime, int availableSeats) {
		super();
		this.showId = showId;
		this.movie = movie;
		this.showDate = showDate;
		this.showTime = showTime;
		this.availableSeats = availableSeats;
	}



	public Long getShowId() {
		return showId;
	}



	public void setShowId(Long showId) {
		this.showId = showId;
	}



	public Movie getMovie() {
		return movie;
	}



	public void setMovie(Movie movie) {
		this.movie = movie;
	}



	public LocalDate getShowDate() {
		return showDate;
	}



	public void setShowDate(LocalDate showDate) {
		this.showDate = showDate;
	}



	public LocalTime getShowTime() {
		return showTime;
	}



	public void setShowTime(LocalTime showTime) {
		this.showTime = showTime;
	}



	public int getAvailableSeats() {
		return availableSeats;
	}



	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}



	@Override
	public String toString() {
		return "Showtime [showId=" + showId + ", showDate=" + showDate + ", showTime=" + showTime + ", availableSeats="
				+ availableSeats + "]";
	}



	
	
}