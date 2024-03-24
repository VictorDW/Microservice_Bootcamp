package com.pragma.bootcamp.adapters.driving.http.dto.response;

import java.util.List;

public record CapacityResponse(
        Long id,
        String name,
        String description,
        List<TechnologyBasicResponse> technologies
) {
}
