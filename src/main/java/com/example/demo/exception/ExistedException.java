package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistedException extends RuntimeException {
  public ExistedException(String message) {
    super(message);
  }
}
