package com.capgemini.CineBuzz;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.capgemini.CineBuzz.controllers.ShowtimeController;
import com.capgemini.CineBuzz.entities.Movie;
import com.capgemini.CineBuzz.entities.Showtime;
import com.capgemini.CineBuzz.services.ShowtimeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShowtimeControllerTest {

    @Mock
    private ShowtimeService showtimeService;

    @InjectMocks
    private ShowtimeController showtimeController;

    private Movie mockMovie;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMovie = new Movie(); // You can set mock fields if needed
    }

    @Test
    public void testGetAllShowtimes() {
        Showtime s1 = new Showtime(1L, mockMovie, LocalDate.now(), LocalTime.of(12, 0), 100);
        Showtime s2 = new Showtime(2L, mockMovie, LocalDate.now(), LocalTime.of(15, 0), 80);
        when(showtimeService.getAllShowtimes()).thenReturn(Arrays.asList(s1, s2));

        ResponseEntity<List<Showtime>> response = showtimeController.getAllShowtimes();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    public void testGetShowtimeById_Found() {
        Long showId = 1L;
        Showtime mockShowtime = new Showtime(showId, mockMovie, LocalDate.now(), LocalTime.of(18, 0), 90);
        when(showtimeService.getShowtimeById(showId)).thenReturn(mockShowtime);

        ResponseEntity<Showtime> response = showtimeController.getShowtime(showId);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getShowId()).isEqualTo(showId);
    }

    @Test
    public void testGetShowtimeById_NotFound() {
        when(showtimeService.getShowtimeById(99L)).thenReturn(null);

        ResponseEntity<Showtime> response = showtimeController.getShowtime(99L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testCreateShowtime() {
        Showtime input = new Showtime(null, mockMovie, LocalDate.now(), LocalTime.of(14, 0), 100);
        Showtime saved = new Showtime(10L, mockMovie, input.getShowDate(), input.getShowTime(), input.getAvailableSeats());
        when(showtimeService.createShowtime(any(Showtime.class))).thenReturn(saved);

        ResponseEntity<Showtime> response = showtimeController.createShowtime(input);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody().getShowId()).isEqualTo(10L);
    }

    @Test
    public void testUpdateShowtime() {
        Long showId = 5L;
        Showtime input = new Showtime(null, mockMovie, LocalDate.now(), LocalTime.of(16, 0), 120);
        Showtime updated = new Showtime(showId, mockMovie, input.getShowDate(), input.getShowTime(), input.getAvailableSeats());
        when(showtimeService.updateShowtime(eq(showId), any(Showtime.class))).thenReturn(updated);

        ResponseEntity<Showtime> response = showtimeController.updateShowtime(showId, input);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getAvailableSeats()).isEqualTo(120);
    }

    @Test
    public void testPatchShowtime() {
        Long showId = 7L;
        Showtime patch = new Showtime(null, null, null, null, 50);
        Showtime patched = new Showtime(showId, mockMovie, LocalDate.now(), LocalTime.of(20, 0), 50);
        when(showtimeService.patchShowtime(eq(showId), any(Showtime.class))).thenReturn(patched);

        ResponseEntity<Showtime> response = showtimeController.patchShowtime(showId, patch);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getAvailableSeats()).isEqualTo(50);
    }

    @Test
    public void testDeleteShowtime_Success() {
        Long showId = 3L;
        when(showtimeService.deleteShowtime(showId)).thenReturn(true);

        ResponseEntity<Void> response = showtimeController.deleteShowtime(showId);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void testDeleteShowtime_NotFound() {
        Long showId = 4L;
        when(showtimeService.deleteShowtime(showId)).thenReturn(false);

        ResponseEntity<Void> response = showtimeController.deleteShowtime(showId);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testGetShowtimesByMovieId() {
        Long movieId = 101L;
        Showtime s = new Showtime(1L, mockMovie, LocalDate.now(), LocalTime.of(10, 0), 75);
        when(showtimeService.findByMovieId(movieId)).thenReturn(Collections.singletonList(s));

        ResponseEntity<List<Showtime>> response = showtimeController.getShowtimesByMovieId(movieId);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getAvailableSeats()).isEqualTo(75);
    }
}
