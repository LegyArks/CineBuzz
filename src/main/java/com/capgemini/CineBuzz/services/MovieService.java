package com.capgemini.CineBuzz.services;




import java.util.List;


import com.capgemini.CineBuzz.entities.Movie;

public interface MovieService {
    List<Movie> getAllMovies();

    Movie getMovieById(Long movieId);

    Movie createMovie(Movie movie);

    Movie updateMovie(Long movieId, Movie movie);

    Movie patchMovie(Long movieId, Movie movie);

    boolean deleteMovie(Long movieId);
   
}
