package com.pragma.bootcamp.configuration.exceptionhandler;

import org.springframework.validation.FieldError;

public record ExceptionArgumentResponse(
    String fieldName,
    String message
) {
  public ExceptionArgumentResponse(FieldError error) {
    this(error.getField(), error.getDefaultMessage());
  }
}
