package com.capgemini.CineBuzz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.entities.Showtime;
import com.capgemini.CineBuzz.exceptions.ShowtimeNotFoundException;
import com.capgemini.CineBuzz.repositories.ShowtimeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public List<Showtime> getAllShowtimes() {
        log.info("Fetching all showtimes");
        return showtimeRepository.findAll();
    }

    @Override
    public Showtime getShowtimeById(Long showId) {
        log.info("Fetching showtime by ID: {}", showId);
        return showtimeRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("Showtime not found with ID: {}", showId);
                    return new ShowtimeNotFoundException("Showtime not found with showId " + showId);
                });
    }

    @Override
    public Showtime createShowtime(Showtime showtime) {
        log.info("Creating new showtime for movie ID: {}", showtime.getMovie().getId());
        return showtimeRepository.save(showtime);
    }

    @Override
    public Showtime updateShowtime(Long showId, Showtime updatedShowtime) {
        log.info("Updating showtime with ID: {}", showId);
        Showtime existingShowtime = showtimeRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("Cannot update. Showtime not found with ID: {}", showId);
                    return new ShowtimeNotFoundException("Showtime not found with showId " + showId);
                });

        existingShowtime.setMovie(updatedShowtime.getMovie());
        existingShowtime.setShowDate(updatedShowtime.getShowDate());
        existingShowtime.setShowTime(updatedShowtime.getShowTime());
        existingShowtime.setAvailableSeats(updatedShowtime.getAvailableSeats());

        log.info("Saving updated showtime for ID: {}", showId);
        return showtimeRepository.save(existingShowtime);
    }

    @Override
    public Showtime patchShowtime(Long showId, Showtime patch) {
        log.info("Patching showtime with ID: {}", showId);
        Showtime existingShowtime = showtimeRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("Cannot patch. Showtime not found with ID: {}", showId);
                    return new ShowtimeNotFoundException("Showtime not found with showId: " + showId);
                });

        if (patch.getMovie() != null) {
            existingShowtime.setMovie(patch.getMovie());
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

        log.info("Saving patched showtime with ID: {}", showId);
        return showtimeRepository.save(existingShowtime);
    }

    @Override
    public boolean deleteShowtime(Long showId) {
        log.info("Attempting to delete showtime with ID: {}", showId);
        if (!showtimeRepository.existsById(showId)) {
            log.warn("Cannot delete. Showtime not found with ID: {}", showId);
            throw new ShowtimeNotFoundException("Showtime not found with id " + showId);
        }
        showtimeRepository.deleteById(showId);
        log.info("Showtime deleted with ID: {}", showId);
        return true;
    }

    @Override
    public List<Showtime> findByMovieId(Long movieId) {
        log.info("Fetching showtimes for movie ID: {}", movieId);
        return showtimeRepository.findByMovieId(movieId);
    }
}
