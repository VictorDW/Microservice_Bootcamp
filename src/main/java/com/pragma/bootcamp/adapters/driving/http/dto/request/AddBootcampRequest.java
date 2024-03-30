package com.pragma.bootcamp.adapters.driving.http.dto.request;

import java.util.List;

public record AddBootcampRequest(
    String name,
    String description,
    List<CapacitiesBasicRequest> capacities
) {
}
