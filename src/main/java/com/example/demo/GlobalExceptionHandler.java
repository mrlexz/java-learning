package com.example.demo;


import com.example.demo.exception.ExistedException;
import com.example.demo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends Throwable {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
    return errors;
  };

  @ExceptionHandler(ExistedException.class)
  public ResponseEntity<Map<String, String>> handleExistedException(ExistedException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Conflict");
    response.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "NotFound");
    response.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}
