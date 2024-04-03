package com.pragma.bootcamp.configuration.exceptionhandler;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoEntityFoundException;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.configuration.exceptionhandler.dto.ExceptionArgumentResponse;
import com.pragma.bootcamp.configuration.exceptionhandler.dto.ExceptionResponse;
import com.pragma.bootcamp.domain.exception.ModelDomainException;
import com.pragma.bootcamp.domain.exception.NoDataFoundException;
import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionManager {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  private ResponseEntity<ExceptionResponse> generalExceptionHandler(String exceptionMessage, HttpStatus httpStatus) {

    ExceptionResponse response = new ExceptionResponse(
        formatter.format(LocalDateTime.now()),
        httpStatus.value(),
        httpStatus.getReasonPhrase(),
        exceptionMessage
    );

    return new ResponseEntity<>(response, httpStatus);
  }
  @ExceptionHandler(AlreadyExistException.class)
  public ResponseEntity<ExceptionResponse> handlerAlreadyExistException(AlreadyExistException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ExceptionArgumentResponse>> handlerArgumentInvalidException(MethodArgumentNotValidException exception) {

    var errors = exception.getFieldErrors()
        .stream()
        .map(ExceptionArgumentResponse::new)
        .toList();

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<List<ExceptionArgumentResponse>> handlerCommandInvalid(ConstraintViolationException exception) {

    var errors = exception.getConstraintViolations()
        .stream()
        .map(ExceptionArgumentResponse::new)
        .toList();

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ExceptionArgumentResponse> handlerTypeInvalid(MethodArgumentTypeMismatchException exception) {
    return ResponseEntity.badRequest().body(
        new ExceptionArgumentResponse(exception.getName(), exception.getMessage())
    );
  }

  @ExceptionHandler(NoDataFoundException.class)
  public ResponseEntity<ExceptionResponse> handlerNoDataFoundException(NoDataFoundException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoEntityFoundException.class)
  public ResponseEntity<ExceptionResponse> handlerNoEntityFoundException(NoEntityFoundException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ModelDomainException.class)
  public ResponseEntity<ExceptionResponse> handlerNoDataFoundException(ModelDomainException exception) {
    return this.generalExceptionHandler(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<ExceptionResponse> handlerDateTimeParseException() {
    return this.generalExceptionHandler(Constants.INVALID_FORMAT_TO_PARSE_DATE, HttpStatus.BAD_REQUEST);
  }

}
