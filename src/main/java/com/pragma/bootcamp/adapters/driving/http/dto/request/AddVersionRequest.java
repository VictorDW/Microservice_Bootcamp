package com.pragma.bootcamp.adapters.driving.http.dto.request;

import com.pragma.bootcamp.adapters.util.ValidDate;

public record AddVersionRequest(
    String bootcampName,
    @ValidDate
    String endDate,
    @ValidDate
    String startDate,
    Integer maximumCapacity
) { }
