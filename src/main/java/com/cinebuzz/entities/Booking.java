package com.cinebuzz.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "booking")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	@ManyToOne
	@JoinColumn(name="userId" , referencedColumnName = "userId")
	private User user;
	@ManyToOne
	@JoinColumn(name="showId" , referencedColumnName = "showId")
	private Showtime showtime;
	
	@Min(value = 1, message = "At least one seat must be booked")
	private int seatsBooked;
	
	@NotNull(message = "Booking date is required")
    @PastOrPresent(message = "Booking date cannot be in the future")
	private LocalDate bookingDate;
	
	@NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be at least 1")
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

