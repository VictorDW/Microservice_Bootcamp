package com.pragma.bootcamp.adapters.driving.http.dto.request;

import com.pragma.bootcamp.adapters.driving.http.util.ValidDate;
import com.pragma.bootcamp.configuration.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record AddVersionRequest(
    @NotBlank(message = "{"+ Constants.FIELD_EMPTY_MESSAGE+"}")
    @Pattern(regexp = Constants.PATTERN_NAME, message = "{"+Constants.SPECIAL_CHARACTER_MESSAGE+"}")
    String bootcampName,

    @ValidDate
    String endDate,

    @ValidDate
    String startDate,

    @NotNull
    @Min(value = 1, message = "{"+Constants.MINIMUM_CAPACITY_MESSAGE+"}")
    Integer maximumCapacity
) { }
