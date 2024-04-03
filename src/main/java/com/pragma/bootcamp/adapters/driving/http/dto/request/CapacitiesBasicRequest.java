package com.pragma.bootcamp.adapters.driving.http.dto.request;

import com.pragma.bootcamp.configuration.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CapacitiesBasicRequest(
		@NotBlank(message = "{"+ Constants.FIELD_EMPTY_MESSAGE+"}")
		@Pattern(regexp = Constants.PATTERN_NAME, message = "{"+Constants.SPECIAL_CHARACTER_MESSAGE+"}")
		@Size(min = 7, max = 50, message = "{"+Constants.NAME_SIZE_CAPACITY_MESSAGE+"}")
    String name

) { }