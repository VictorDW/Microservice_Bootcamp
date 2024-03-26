package com.pragma.bootcamp.domain.util;

public record PaginationData(
    Integer page,
    Integer size,
    String direction,
    String property
) { }
