package com.pragma.bootcamp.adapters.driving.http.dto.response;

import java.util.List;

public record CapacityBasicResponse(
    Long id,
    String name,
    List<TechnologyBasicResponse> technologies
) {
}
