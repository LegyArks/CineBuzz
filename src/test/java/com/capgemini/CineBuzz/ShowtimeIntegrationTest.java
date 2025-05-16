package com.capgemini.CineBuzz;

import com.capgemini.CineBuzz.entities.Movie;
import com.capgemini.CineBuzz.entities.Showtime;
import com.capgemini.CineBuzz.repositories.MovieRepository;
import com.capgemini.CineBuzz.services.ShowtimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShowtimeIntegrationTest {

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;

    @BeforeEach
    void setup() {
        movieRepository.deleteAll();

        movie = new Movie();
        movie.setTitle("Interstellar");
        movie.setGenre("Sci-Fi");
        movie.setDuration(169);
        movie.setPrice(15.0);
        movie = movieRepository.save(movie);

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setShowDate(LocalDate.now());
        showtime.setShowTime(LocalTime.of(20, 0));
        showtime.setAvailableSeats(100);

        showtimeService.createShowtime(showtime);
    }

    @Test
    void testGetAllShowtimes() {
        List<Showtime> showtimes = showtimeService.getAllShowtimes();
        assertThat(showtimes).isNotEmpty();
        assertThat(showtimes.get(0).getMovie().getTitle()).isEqualTo("Interstellar");
    }

    @Test
    void testFindByMovieId() {
        List<Showtime> showtimes = showtimeService.findByMovieId(movie.getId());
        assertThat(showtimes).hasSize(1);
        assertThat(showtimes.get(0).getAvailableSeats()).isEqualTo(100);
    }
}
