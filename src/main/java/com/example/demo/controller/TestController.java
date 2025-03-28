package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
  @GetMapping
  public ResponseEntity<?> test() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("User: " + auth.getName());
    System.out.println("Authorities: " + auth.getAuthorities());
    return ResponseEntity.ok("Success");
  }
}
