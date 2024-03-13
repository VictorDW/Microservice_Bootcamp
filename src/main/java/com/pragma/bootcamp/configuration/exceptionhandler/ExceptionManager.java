package com.pragma.bootcamp.configuration.exceptionhandler;

import com.pragma.bootcamp.domain.exception.TechnologyAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ExceptionManager {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  public ResponseEntity<ExceptionResponse> generalExceptionHandler(String exceptionMessage, HttpStatus httpStatus) {

    ExceptionResponse response = new ExceptionResponse(
        formatter.format(LocalDateTime.now()),
        httpStatus.value(),
        httpStatus.getReasonPhrase(),
        exceptionMessage
    );

    return new ResponseEntity<>(response, httpStatus);
  }

  @ExceptionHandler(TechnologyAlreadyExistException.class)
  public ResponseEntity<ExceptionResponse> handlerTechnologyAlreadyExistException(TechnologyAlreadyExistException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
  }
}
