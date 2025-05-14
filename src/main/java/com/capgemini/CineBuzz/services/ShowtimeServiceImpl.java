package com.capgemini.CineBuzz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.entities.Showtime;
import com.capgemini.CineBuzz.repositories.ShowtimeRepository;



@Service
public class ShowtimeServiceIMPL implements ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeServiceIMPL(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    @Override
    public Showtime getShowtimeById(Long showId) {
        return showtimeRepository.findById(showId)
                .orElseThrow(() -> new ShowtimeNotFoundException("Showtime not found with showId " + showId));
    }

    @Override
    public Showtime createShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    @Override
    public Showtime updateShowtime(Long showId, Showtime updatedShowtime) {
        Showtime existingShowtime = showtimeRepository.findById(showId)
                .orElseThrow(() -> new ShowtimeNotFoundException("Showtime not found with showId " + showId));
        existingShowtime.setMovieId(updatedShowtime.getMovieId());
        existingShowtime.setShowDate(updatedShowtime.getShowDate());
        existingShowtime.setShowTime(updatedShowtime.getShowTime());
        existingShowtime.setAvailableSeats(updatedShowtime.getAvailableSeats());
        return showtimeRepository.save(existingShowtime);
    }

    @Override
    public Showtime patchShowtime(Long showId, Showtime patch) {
        Showtime existingShowtime = showtimeRepository.findById(showId)
                .orElseThrow(() -> new ShowtimeNotFoundException("Showtime not found with showId: " + showId));

        if (patch.getMovieId() != null) {
            existingShowtime.setMovieId(patch.getMovieId());
        }
        if (patch.getShowDate() != null) {
            existingShowtime.setShowDate(patch.getShowDate());
        }
        if (patch.getShowTime() != null) {
            existingShowtime.setShowTime(patch.getShowTime());
        }
        if (patch.getAvailableSeats() > 0) {
            existingShowtime.setAvailableSeats(patch.getAvailableSeats());
        }

        return showtimeRepository.save(existingShowtime);
    }

    @Override
    public boolean deleteShowtime(Long showId) {
        if (!showtimeRepository.existsById(showId)) {
            throw new ShowtimeNotFoundException("Showtime not found with id " + showId);
        }
        showtimeRepository.deleteById(showId);
        return true;
    }

    @Override
    public List<Showtime> findByMovieId(Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }
}
