package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "movies")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "UUID")
  private UUID id;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private int duration;

  @Column(name = "release_date")
  private LocalDate releaseDate;


}
