package com.cinebuzz;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.cinebuzz.controllers.MovieController;
import com.cinebuzz.entities.Movie;
import com.cinebuzz.services.MovieService;

import java.util.Arrays;
import java.util.List;

class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private Movie sampleMovie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleMovie = new Movie(1L, "Inception", "Sci-Fi", 148, "English", 200.0, false, null, "Dream invasion", "trailer.mp4");
    }

    @Test
    void testGetAllMovies() {
        when(movieService.getAllMovies()).thenReturn(Arrays.asList(sampleMovie));

        ResponseEntity<List<Movie>> response = movieController.getAllMovies();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("Inception", response.getBody().get(0).getTitle());
    }

    @Test
    void testGetMovieById() {
        when(movieService.getMovieById(1L)).thenReturn(sampleMovie);

        ResponseEntity<Movie> response = movieController.getMovie(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Inception", response.getBody().getTitle());
    }

    @Test
    void testCreateMovie() {
    	BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);
        
        when(movieService.createMovie(sampleMovie)).thenReturn(sampleMovie);

        ResponseEntity<Movie> response = movieController.createMovie(sampleMovie, mockBindingResult);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("Inception", response.getBody().getTitle());
        assertTrue(response.getHeaders().getLocation().toString().contains("/api/movies/1"));
    }

    @Test
    void testUpdateMovie() {
    	BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);
    	
        when(movieService.updateMovie(eq(1L), any(Movie.class))).thenReturn(sampleMovie);

        ResponseEntity<Movie> response = movieController.updateMovie(1L, sampleMovie, mockBindingResult);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Inception", response.getBody().getTitle());
    }

    @Test
    void testPatchMovie() {
    	
    	BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);
        
        when(movieService.patchMovie(eq(1L), any(Movie.class))).thenReturn(sampleMovie);

        ResponseEntity<Movie> response = movieController.patchMovie(1L, sampleMovie, mockBindingResult);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Inception", response.getBody().getTitle());
    }

    @Test
    void testDeleteMovie_Success() {
        when(movieService.deleteMovie(1L)).thenReturn(true);

        ResponseEntity<Void> response = movieController.deleteMovie(1L);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testDeleteMovie_NotFound() {
        when(movieService.deleteMovie(2L)).thenReturn(false);

        ResponseEntity<Void> response = movieController.deleteMovie(2L);

        assertEquals(404, response.getStatusCode().value());
    }
}
