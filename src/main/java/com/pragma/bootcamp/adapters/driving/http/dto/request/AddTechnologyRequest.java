package com.pragma.bootcamp.adapters.driving.http.dto.request;

import com.pragma.bootcamp.configuration.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddTechnologyRequest(
    @NotBlank(message = "{"+Constants.FIELD_EMPTY_MESSAGE+"}")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "{"+Constants.ONLY_LETTERS_MESSAGE+"}")
    @Size(min = 2, max = 50, message = "{"+Constants.NAME_SIZE_MESSAGE+"}")
    String name,

    @NotBlank(message = "{"+Constants.FIELD_EMPTY_MESSAGE+"}")
    @Pattern(regexp = "^[A-Za-z0-9\\sáéíóúÁÉÍÓÚñÑ]+$", message = "{"+Constants.SPECIAL_CHARACTER_MESSAGE+"}")
    @Size(min = 10, max = 90, message = "{"+Constants.DESCRIPTION_SIZE_MESSAGE+"}")
    String description
) {
}
