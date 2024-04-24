package com.pragma.bootcamp.domain.exception;

public class ModelDomainException extends RuntimeException{
    public ModelDomainException(String message) {
        super(message);
    }
}
