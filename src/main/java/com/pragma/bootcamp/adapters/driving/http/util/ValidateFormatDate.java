package com.pragma.bootcamp.adapters.driving.http.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidateFormatDate implements ConstraintValidator<ValidDate, String> {
  @Override
  public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    try {
      return validateFutureAndPresentDate(LocalDate.parse(date, formatter));
    } catch (DateTimeException e) {
      return false;
    }
  }

  private Boolean validateFutureAndPresentDate(LocalDate date) {
    LocalDate now = LocalDate.now();
    return date.isAfter(now) || date.isEqual(now);
  }
}
