package com.example.demo.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter @Setter
public class MovieRequestDTO {

  @NotBlank(message = "Title can't be empty")
  @Size(max = 255, message = "Title can't exceed 255 characters")
  private String title;

  private String description;

  @Min(value = 10, message = "Duration must be at least 10 minutes")
  private int duration;

  @NotNull(message = "Release Date can't be null")
  private LocalDate releaseDate;
}
