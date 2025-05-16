package com.capgemini.CineBuzz.dto;

import java.time.LocalDate;

public class BookingRequestDTO {

  
    private Long userId;


    private Long movieId;


    private Long showId;

    private int seatsBooked;


    private LocalDate bookingDate;


    private Long amount;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(Long userId, Long movieId, Long showId, int seatsBooked, LocalDate bookingDate, Long amount) {
        this.userId = userId;
        this.movieId = movieId;
        this.showId = showId;
        this.seatsBooked = seatsBooked;
        this.bookingDate = bookingDate;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
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
}
