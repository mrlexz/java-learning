package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String encodePassword(String rawPassword) {
    return encoder.encode(rawPassword);
  }

  public static boolean isMatch(String rawPassword, String encodedPassword) {
    return encoder.matches(rawPassword, encodedPassword);
  }
}
