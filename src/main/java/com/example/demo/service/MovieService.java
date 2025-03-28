package com.example.demo.service;

import com.example.demo.dto.MovieRequestDTO;
import com.example.demo.entity.Movie;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {
  public final MovieRepository movieRepository;

  public Page<Movie> getAllMovies(String title, int page, int size, String sortBy) {
    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).ascending());
    if (title == null || title.trim().isEmpty()) {
      return movieRepository.findAll(pageable);
    }
    return movieRepository.searchByTitle(title, pageable);
  }

  public Movie addMovie(MovieRequestDTO movieRequest) {
    Movie movie = new Movie();
    movie.setTitle(movieRequest.getTitle());
    movie.setDescription(movieRequest.getDescription());
    movie.setReleaseDate(movieRequest.getReleaseDate());
    movie.setDuration(movieRequest.getDuration());
    return movieRepository.save(movie);
  }

  public Movie updateMovie(UUID id, MovieRequestDTO movieRequest) {
    Movie movie = movieRepository.findById(id).map(mv -> {
      mv.setTitle(movieRequest.getTitle());
      mv.setDescription(movieRequest.getDescription());
      mv.setReleaseDate(movieRequest.getReleaseDate());
      mv.setDuration(movieRequest.getDuration());
      return movieRepository.save(mv);
    }).orElseThrow(() -> new NotFoundException("Movie not found with id: " + id));
    return movie;
  }

  public Movie getMovie(UUID id) {
    return movieRepository.findById(id).orElse(null);
  }

  public void deleteMovie(UUID id) {
    try {
      movieRepository.deleteById(id);
    } catch (Exception e) {
      throw new NotFoundException("Movie not found with id: " + id);
    }
  }
}
