package com.pragma.bootcamp.domain.exception;

public class CapacityAlreadyExistException extends RuntimeException{
  public CapacityAlreadyExistException(String message) {
    super(message);
  }
}
