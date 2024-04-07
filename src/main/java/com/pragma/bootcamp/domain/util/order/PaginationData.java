package com.pragma.bootcamp.domain.util.order;

public record PaginationData(
    Integer page,
    Integer size,
    String direction,
    String property
) { }
