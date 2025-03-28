package com.example.demo.service;

import com.example.demo.constant.Role;
import com.example.demo.dto.UserLoginDAO;
import com.example.demo.dto.UserRegisterDAO;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final UserService userService;
  private final PasswordUtils passwordUtils;

  public User login(UserLoginDAO userLoginDAO) {
    if (userRepository.existsByUsername(userLoginDAO.getUsername())) {
      User user = userRepository.findByUsername(userLoginDAO.getUsername());
      if (passwordUtils.isMatch(userLoginDAO.getPassword(), user.getPassword())) {
        return user;
      }
      return null;
    }
    return null;
  }

  public User register(UserRegisterDAO userRegisterDAO) {
    UserRequestDTO userRequestDTO = new UserRequestDTO();
    userRequestDTO.setUsername(userRegisterDAO.getUsername());
    userRequestDTO.setEmail(userRegisterDAO.getEmail());
    userRequestDTO.setPassword(userRegisterDAO.getPassword());

    if (userRegisterDAO.isAdmin()) {
      userRequestDTO.setRole(Role.ADMIN);
    }

    User newUser = userService.addUser(userRequestDTO);
    return userRepository.findById(newUser.getId()).orElse(null);
  }
}
