package com.pragma.bootcamp.configuration.exceptionhandler.dto;

public record ExceptionResponse(
    String localDateTime,
    int status,
    String error,
    String message
) {}
