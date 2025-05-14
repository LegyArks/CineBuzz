package com.capgemini.CineBuzz.entities;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	@ManyToOne
	@JoinColumn(name="userId" , referencedColumnName = "userId")
	private User user;
	@ManyToOne
	@JoinColumn(name="showId" , referencedColumnName = "showId")
	@JsonIgnore
	private Showtime showtime;
	private int seatsBooked;
	private LocalDate bookingDate;
	private Long amount;

	public Booking() {
		
	}

	public Booking(Long bookingId, User user, Showtime showtime, int seatsBooked, LocalDate bookingDate, Long amount) {
		super();
		this.bookingId = bookingId;
		this.user = user;
		this.showtime = showtime;
		this.seatsBooked = seatsBooked;
		this.bookingDate = bookingDate;
		this.amount = amount;
	}

	public Booking(Long bookingId, int seatsBooked, LocalDate bookingDate, Long amount) {
		super();
		this.bookingId = bookingId;
		this.seatsBooked = seatsBooked;
		this.bookingDate = bookingDate;
		this.amount = amount;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Showtime getShowtime() {
		return showtime;
	}

	public void setShowtime(Showtime showtime) {
		this.showtime = showtime;
	}

	public int getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", user=" + user + ", seatsBooked=" + seatsBooked + ", bookingDate="
				+ bookingDate + ", amount=" + amount + "]";
	}
	
	
};

