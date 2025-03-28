package com.example.demo.dto;

import com.example.demo.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter @Setter
@ToString
public class UserRequestDTO {
  @NotBlank(message = "User name is required")
  @Size(min = 1, max = 100)
  private String username;

  @NotBlank(message = "Email is required")
  @Size(min = 1, max = 100)
  @Email
  private String email;

  @NotBlank(message = "Password is required")
  private String password;

  private Role role;

}
