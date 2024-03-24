package com.pragma.bootcamp.adapters.driving.http.dto.request;

import java.util.List;

public record AddCapacityRequest(
        String name,
        String description,
        List<TechnologyBasicRequest> technologyBasicList
) { }
