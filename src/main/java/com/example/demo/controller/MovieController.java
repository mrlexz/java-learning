package com.example.demo.controller;

import com.example.demo.dto.MovieRequestDTO;
import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllMovies(
          @RequestParam(required = false) String name,
          @RequestParam(defaultValue = "1") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "title") String sortBy
  ) {
    Page<Movie> movies = movieService.getAllMovies(name, page, size, sortBy);
    Map<String, Object> response = new HashMap<>();
    response.put("movie_list", movies.getContent());
    response.put("total_pages", movies.getTotalPages());
    response.put("total_results", movies.getNumberOfElements());
    response.put("page_number", movies.getNumber() + 1);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieRequestDTO movieRequest) {
    Movie movie = movieService.addMovie(movieRequest);
    return ResponseEntity.ok(movie);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Map<String, Object>> getMovie(@PathVariable UUID id) {
    System.out.println(id);
    Movie movie = movieService.getMovie(id);
    Map<String, Object> response = new HashMap<>();
    response.put("movie", movie);

    return ResponseEntity.ok(response);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Map<String, Object>> updateMovie(@PathVariable UUID id, @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
    Movie movieUpdated = movieService.updateMovie(id, movieRequestDTO);
    Map<String, Object> response = new HashMap<>();
    response.put("movie", movieUpdated);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Map<String, Object>> deleteMovie(@PathVariable UUID id) {
    movieService.deleteMovie(id);
    Map<String, Object> response = new HashMap<>();
    response.put("movieId", id);
    response.put("message","Xoa thanh cong");
    return ResponseEntity.ok(response);
  }




}
