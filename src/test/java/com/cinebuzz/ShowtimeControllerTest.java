package com.cinebuzz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.cinebuzz.controllers.ShowtimeController;
import com.cinebuzz.entities.Movie;
import com.cinebuzz.entities.Showtime;
import com.cinebuzz.services.ShowtimeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShowtimeControllerTest {

    @Mock
    private ShowtimeService showtimeService;

    @InjectMocks
    private ShowtimeController showtimeController;

    private Movie mockMovie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMovie = new Movie(); // Or mock(Movie.class)
    }

    @Test
    void getAllShowtimes_returnsList() {
        Showtime s1 = new Showtime(1L, mockMovie, LocalDate.now(), LocalTime.NOON, 100);
        Showtime s2 = new Showtime(2L, mockMovie, LocalDate.now(), LocalTime.of(15, 0), 80);
        when(showtimeService.getAllShowtimes()).thenReturn(List.of(s1, s2));

        ResponseEntity<List<Showtime>> response = showtimeController.getAllShowtimes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getShowtimeById_found() {
        Showtime s = new Showtime(1L, mockMovie, LocalDate.now(), LocalTime.of(18, 0), 90);
        when(showtimeService.getShowtimeById(1L)).thenReturn(s);

        ResponseEntity<Showtime> response = showtimeController.getShowtime(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getShowId());
    }

    @Test
    void getShowtimeById_notFound() {
        when(showtimeService.getShowtimeById(99L)).thenReturn(null);

        ResponseEntity<Showtime> response = showtimeController.getShowtime(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createShowtime_success() {
    	BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);
        
        Showtime input = new Showtime(null, mockMovie, LocalDate.now(), LocalTime.of(14, 0), 100);
        Showtime saved = new Showtime(10L, mockMovie, input.getShowDate(), input.getShowTime(), input.getAvailableSeats());

        when(showtimeService.createShowtime(input)).thenReturn(saved);

        ResponseEntity<Showtime> response = showtimeController.createShowtime(input,mockBindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(10L, response.getBody().getShowId());
    }

    @Test
    void updateShowtime_success() {
    	BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);
        
        Showtime updated = new Showtime(5L, mockMovie, LocalDate.now(), LocalTime.of(16, 0), 120);

        when(showtimeService.updateShowtime(eq(5L), any(Showtime.class))).thenReturn(updated);

        ResponseEntity<Showtime> response = showtimeController.updateShowtime(5L, updated, mockBindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(120, response.getBody().getAvailableSeats());
    }

    @Test
    void patchShowtime_success() {
    	BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);
        
        Showtime patch = new Showtime(null, null, null, null, 50);
        Showtime patched = new Showtime(7L, mockMovie, LocalDate.now(), LocalTime.of(20, 0), 50);

        when(showtimeService.patchShowtime(eq(7L), any())).thenReturn(patched);

        ResponseEntity<Showtime> response = showtimeController.patchShowtime(7L, patch, mockBindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(50, response.getBody().getAvailableSeats());
    }

    @Test
    void deleteShowtime_success() {
        when(showtimeService.deleteShowtime(3L)).thenReturn(true);

        ResponseEntity<Void> response = showtimeController.deleteShowtime(3L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteShowtime_notFound() {
        when(showtimeService.deleteShowtime(4L)).thenReturn(false);

        ResponseEntity<Void> response = showtimeController.deleteShowtime(4L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getShowtimesByMovieId_success() {
        Showtime s = new Showtime(1L, mockMovie, LocalDate.now(), LocalTime.of(10, 0), 75);
        when(showtimeService.findByMovieId(101L)).thenReturn(List.of(s));

        ResponseEntity<List<Showtime>> response = showtimeController.getShowtimesByMovieId(101L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(75, response.getBody().get(0).getAvailableSeats());
    }
}
