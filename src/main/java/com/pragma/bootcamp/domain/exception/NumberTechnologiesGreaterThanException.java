package com.pragma.bootcamp.domain.exception;

public class NumberTechnologiesGreaterThanException extends CapacityDomainException{
  public NumberTechnologiesGreaterThanException(String message) {
    super(message);
  }
}
