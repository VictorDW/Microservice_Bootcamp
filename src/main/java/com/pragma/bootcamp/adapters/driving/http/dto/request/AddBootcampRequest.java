package com.pragma.bootcamp.adapters.driving.http.dto.request;

import com.pragma.bootcamp.configuration.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AddBootcampRequest(
		@NotBlank(message = "{"+ Constants.FIELD_EMPTY_MESSAGE+"}")
		@Pattern(regexp = Constants.PATTERN_NAME, message = "{"+Constants.SPECIAL_CHARACTER_MESSAGE+"}")
		@Size(min = 10, max = 50, message = "{"+Constants.NAME_SIZE_BOOTCAMP_MESSAGE+"}")
    String name,

		@NotBlank(message = "{"+Constants.FIELD_EMPTY_MESSAGE+"}")
		@Pattern(regexp = Constants.PATTERN_DESCRIPTION, message = "{"+Constants.SPECIAL_CHARACTER_MESSAGE+"}")
		@Size(min = 10, max = 90, message = "{"+Constants.DESCRIPTION_SIZE_MESSAGE+"}")
    String description,
    List<CapacitiesBasicRequest> capacities
) {
}