package com.pragma.bootcamp.adapters.driven.jpa.mysql.exception;

public class NoEntityFoundException extends RuntimeException{
    public NoEntityFoundException(String message) {
        super(message);
    }
}
