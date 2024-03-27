package com.pragma.bootcamp.configuration.exceptionhandler;

public record ExceptionResponse(
    String localDateTime,
    int status,
    String error,
    String message
) {}
