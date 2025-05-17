package com.cinebuzz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.cinebuzz.entities.Movie;
import com.cinebuzz.entities.Showtime;
import com.cinebuzz.exceptions.ShowtimeNotFoundException;
import com.cinebuzz.repositories.ShowtimeRepository;
import com.cinebuzz.services.ShowtimeServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowtimeServiceImplTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private ShowtimeServiceImpl showtimeService;

    private Showtime sampleShowtime;
    private Movie sampleMovie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleMovie = new Movie(); // Can set details if needed
        sampleShowtime = new Showtime(1L, sampleMovie, LocalDate.now(), LocalTime.of(14, 0), 100);
    }

    @Test
    void testGetAllShowtimes() {
        when(showtimeRepository.findAll()).thenReturn(List.of(sampleShowtime));

        List<Showtime> result = showtimeService.getAllShowtimes();

        assertEquals(1, result.size());
        assertEquals(100, result.get(0).getAvailableSeats());
    }

    @Test
    void testGetShowtimeById_Found() {
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(sampleShowtime));

        Showtime result = showtimeService.getShowtimeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getShowId());
    }

    @Test
    void testGetShowtimeById_NotFound() {
        when(showtimeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ShowtimeNotFoundException.class, () -> showtimeService.getShowtimeById(1L));
    }

    @Test
    void testCreateShowtime() {
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(sampleShowtime);

        Showtime result = showtimeService.createShowtime(sampleShowtime);

        assertEquals(100, result.getAvailableSeats());
    }

    @Test
    void testUpdateShowtime_Success() {
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(sampleShowtime));
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(sampleShowtime);

        sampleShowtime.setAvailableSeats(150);
        Showtime result = showtimeService.updateShowtime(1L, sampleShowtime);

        assertEquals(150, result.getAvailableSeats());
    }

    @Test
    void testUpdateShowtime_NotFound() {
        when(showtimeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ShowtimeNotFoundException.class, () -> showtimeService.updateShowtime(1L, sampleShowtime));
    }

    @Test
    void testPatchShowtime_Success() {
        Showtime patch = new Showtime();
        patch.setAvailableSeats(75);

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(sampleShowtime));
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(sampleShowtime);

        Showtime result = showtimeService.patchShowtime(1L, patch);

        assertEquals(75, result.getAvailableSeats());
    }

    @Test
    void testDeleteShowtime_Success() {
        when(showtimeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(showtimeRepository).deleteById(1L);

        assertTrue(showtimeService.deleteShowtime(1L));
        verify(showtimeRepository).deleteById(1L);
    }

    @Test
    void testDeleteShowtime_NotFound() {
        when(showtimeRepository.existsById(1L)).thenReturn(false);

        assertThrows(ShowtimeNotFoundException.class, () -> showtimeService.deleteShowtime(1L));
    }

    @Test
    void testFindByMovieId() {
        when(showtimeRepository.findByMovieId(101L)).thenReturn(List.of(sampleShowtime));

        List<Showtime> result = showtimeService.findByMovieId(101L);

        assertEquals(1, result.size());
    }
}
