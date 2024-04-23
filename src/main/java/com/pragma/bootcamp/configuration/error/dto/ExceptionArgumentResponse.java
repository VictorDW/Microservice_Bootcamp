package com.pragma.bootcamp.configuration.exceptionhandler.dto;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.FieldError;

public record ExceptionArgumentResponse(
    String fieldName,
    String message
) {
  public ExceptionArgumentResponse(FieldError error) {
    this(error.getField(), error.getDefaultMessage());
  }

  public ExceptionArgumentResponse(ConstraintViolation<?> error) {
    this("Request parameter", error.getMessage());
  }
}
