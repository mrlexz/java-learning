package com.example.demo.controller;

import com.example.demo.dto.UserLoginDAO;
import com.example.demo.dto.UserRegisterDAO;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import com.example.demo.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final JwtUtil jwtUtil;


  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody UserLoginDAO userLoginDAO) {
    User user = authService.login(userLoginDAO);
    if (user == null) {
     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid username or password"));
    }
    String token = jwtUtil.generateToken(user.getUsername());
    return ResponseEntity.ok(Map.of("token", token));
  }

  @PostMapping("/register")
  public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserRegisterDAO userRegisterDAO) {
    User user = authService.register(userRegisterDAO);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Cannot register user"));
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "success",
            "token", jwtUtil.generateToken(user.getUsername())
            ));
  }

}
