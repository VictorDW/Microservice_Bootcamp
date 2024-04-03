package com.pragma.bootcamp.adapters.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidateFormatDate implements ConstraintValidator<FormatDate, LocalDate> {
  @Override
  public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      LocalDate.parse(date.toString(), formatter);
      return true;
    } catch (DateTimeException e) {
      return false;
    }
  }
}
