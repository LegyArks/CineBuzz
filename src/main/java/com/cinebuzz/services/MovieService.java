package com.cinebuzz.services;




import java.util.List;

import com.cinebuzz.entities.Movie;

public interface MovieService {
    List<Movie> getAllMovies();

    Movie getMovieById(Long movieId);

    Movie createMovie(Movie movie);

    Movie updateMovie(Long movieId, Movie movie);

    Movie patchMovie(Long movieId, Movie movie);

    boolean deleteMovie(Long movieId);
   
}
