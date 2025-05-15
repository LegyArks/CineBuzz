package com.capgemini.CineBuzz.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.entities.Movie;
import com.capgemini.CineBuzz.exceptions.MovieNotFoundException;
import com.capgemini.CineBuzz.repositories.MovieRepository;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepository;

	@Autowired
	public MovieServiceImpl(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Movie getMovieById(Long movieId) {
		return movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + movieId));
	}

	@Override
	public Movie createMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	@Override
	public Movie updateMovie(Long movieId, Movie updatedMovie) {
		Movie existingMovie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + movieId));

		existingMovie.setTitle(updatedMovie.getTitle());
		existingMovie.setGenre(updatedMovie.getGenre());
		existingMovie.setDuration(updatedMovie.getDuration());
		existingMovie.setLanguage(updatedMovie.getLanguage());
		existingMovie.setPrice(updatedMovie.getPrice());
		existingMovie.setUpcoming(updatedMovie.isUpcoming());
		existingMovie.setImage(updatedMovie.getImage());
		existingMovie.setDescription(updatedMovie.getDescription());
		existingMovie.setTrailer(updatedMovie.getTrailer());


		return movieRepository.save(existingMovie);
	}

	@Override
	public Movie patchMovie(Long movieId, Movie patch) {
		Movie existingMovie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + movieId));

		if (patch.getTitle() != null) {
			existingMovie.setTitle(patch.getTitle());
		}
		if (patch.getGenre() != null) {
			existingMovie.setGenre(patch.getGenre());
		}
		if (patch.getDuration() != 0) {
			existingMovie.setDuration(patch.getDuration());
		}
		if (patch.getLanguage() != null) {
			existingMovie.setLanguage(patch.getLanguage());
		}
		if (patch.getPrice() != 0) {
			existingMovie.setPrice(patch.getPrice());
		}
		if (patch.getImage() != null) {
	        existingMovie.setImage(patch.getImage());
	    }
	    if (patch.getDescription() != null) {
	        existingMovie.setDescription(patch.getDescription());
	    }
	    if (patch.getTrailer() != null) {
	        existingMovie.setTrailer(patch.getTrailer());
	    }
		existingMovie.setUpcoming(patch.isUpcoming());

		return movieRepository.save(existingMovie);
	}

	@Override
	public boolean deleteMovie(Long movieId) {
		if (!movieRepository.existsById(movieId)) {
			throw new MovieNotFoundException("Movie not found with id " + movieId);
		}
		movieRepository.deleteById(movieId);
		return true;
	}
}
