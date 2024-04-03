package com.pragma.bootcamp.adapters.driving.http.dto.response;

import java.util.List;

public record BootcampResponse(
    Long id,
    String name,
    String description,
    List<CapacityBasicResponse> capacities
) { }