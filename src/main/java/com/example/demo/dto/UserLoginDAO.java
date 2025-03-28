package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class UserLoginDAO {

  @NotNull(message = "User name is required")
  public String username;

  @NotNull(message = "Password is required")
  public String password;
}
