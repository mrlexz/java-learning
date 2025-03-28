package com.example.demo.service;

import com.example.demo.constant.Role;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.ExistedException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService  {
  public final UserRepository userRepository;

  public Page<User> getAllUser(String search, int page, int size, String sortBy) {
    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).ascending());
    if (search == null || search.isEmpty()) {
      return userRepository.getAllUser(pageable);
    }
    return userRepository.searchByName(search, pageable);
  }

  public Optional<User> getUserById(UUID id) {
    return Optional.ofNullable(userRepository.findById(id).orElse(null));
  }

  public User addUser(UserRequestDTO userRequestDTO) {

    System.out.println("Username exists: " + userRepository.existsByUsername(userRequestDTO.getUsername()));

    if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
      throw new ExistedException("Username already exists");
    }
    if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
      throw new ExistedException("Email already exists");
    }

    User newUser = new User();
    newUser.setUsername(userRequestDTO.getUsername());
    String encodedPassword = PasswordUtils.encodePassword(userRequestDTO.getPassword());
    newUser.setPassword(encodedPassword);
    newUser.setEmail(userRequestDTO.getEmail());
    if (userRequestDTO.getRole() != null) {
      newUser.setRole(userRequestDTO.getRole());
    } else {
      newUser.setRole(Role.USER);
    }
    return userRepository.save(newUser);
  }

  public void deleteUser(UUID id) {
    try {
      userRepository.softDelete(id);
    } catch (Exception e) {
      throw new NotFoundException("User not found with id: " + id);
    }
  }

}
