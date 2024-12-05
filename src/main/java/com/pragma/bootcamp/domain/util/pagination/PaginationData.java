package com.pragma.bootcamp.domain.util.pagination;

public record PaginationData(
    Integer page,
    Integer size,
    String direction,
    String property
) { }
