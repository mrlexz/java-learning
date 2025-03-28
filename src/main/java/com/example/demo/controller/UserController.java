package com.example.demo.controller;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllUsers(
          @RequestParam(required = false) String searchName,
          @RequestParam(defaultValue = "1") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "username") String sortBy
  ) {
    Page<User> pageUser = userService.getAllUser(searchName, page, size, sortBy);
    return ResponseEntity.ok(Map.of(
            "total_result", pageUser.getNumberOfElements(),
            "user_list", pageUser.getContent(),
            "total_page", pageUser.getTotalPages(),
            "page_number", pageUser.getNumber()
    ));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getUserById(@PathVariable("id") UUID id) {
    Optional<User> user = userService.getUserById(id);
    return ResponseEntity.ok(Map.of(
            "user", user
    ));
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
    User user = userService.addUser(userRequestDTO);
    return ResponseEntity.ok(Map.of("user", Map.of(
            "user_name", user.getUsername(),
            "email", user.getEmail(),
            "role", user.getRole()
    )));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("id") UUID id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(Map.of());
  }
}
