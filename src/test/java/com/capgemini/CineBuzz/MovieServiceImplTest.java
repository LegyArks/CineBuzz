package com.capgemini.CineBuzz;

import com.capgemini.CineBuzz.entities.Movie;

import com.capgemini.CineBuzz.exceptions.MovieNotFoundException;
import com.capgemini.CineBuzz.repositories.MovieRepository;

import com.capgemini.CineBuzz.services.MovieServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie sampleMovie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleMovie = new Movie(1L, "Inception", "Sci-Fi", 148, "English", 12.5, false, "image.jpg", "Great movie", "trailer.mp4");
    }

    @Test
    void testGetAllMovies() {
        List<Movie> movies = List.of(sampleMovie);
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    void testGetMovieById_Found() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(sampleMovie));

        Movie result = movieService.getMovieById(1L);
        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }

    @Test
    void testGetMovieById_NotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MovieNotFoundException.class, () -> movieService.getMovieById(1L));
    }

    @Test
    void testCreateMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(sampleMovie);

        Movie result = movieService.createMovie(sampleMovie);
        assertEquals("Inception", result.getTitle());
    }

    @Test
    void testUpdateMovie_Success() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(sampleMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(sampleMovie);

        sampleMovie.setTitle("Updated Title");
        Movie result = movieService.updateMovie(1L, sampleMovie);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void testUpdateMovie_NotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MovieNotFoundException.class, () -> movieService.updateMovie(1L, sampleMovie));
    }
    
    @Test
    void testPatchMovie_Success() {
        Movie patch = new Movie();
        patch.setTitle("Patched Title");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(sampleMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(sampleMovie);

        Movie result = movieService.patchMovie(1L, patch);
        assertEquals("Patched Title", result.getTitle());
    }

    @Test
    void testDeleteMovie_Success() {
        when(movieRepository.existsById(1L)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(1L);

        assertTrue(movieService.deleteMovie(1L));
        verify(movieRepository).deleteById(1L);
    }

    @Test
    void testDeleteMovie_NotFound() {
        when(movieRepository.existsById(1L)).thenReturn(false);
        assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie(1L));
    }
}
