package com.pragma.bootcamp.configuration.error.dto;

public record ExceptionResponse(
    String localDateTime,
    int status,
    String error,
    String message
) {}
