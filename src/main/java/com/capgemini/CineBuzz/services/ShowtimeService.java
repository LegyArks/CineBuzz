package com.capgemini.CineBuzz.services;

import java.util.List;

import com.capgemini.CineBuzz.entities.Showtime;



public interface ShowtimeService {
    List<Showtime> getAllShowtimes();
    Showtime getShowtimeById(Long showId);
    Showtime createShowtime(Showtime showtime);
    Showtime updateShowtime(Long showId, Showtime showtime);
    Showtime patchShowtime(Long showId, Showtime showtime);
    boolean deleteShowtime(Long showId);
    List<Showtime> findByMovieId(Long movieId);
}
