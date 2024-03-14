package com.pragma.bootcamp.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddTechnologyRequest(
    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "{message.only.letters}")
    @Size(min = 2, max = 50, message = "{message.name.size}")
    String name,

    @NotBlank(message = "{message.empty.field}")
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$", message = "{message.only.letters}")
    @Size(min = 10, max = 90, message = "{message.description.size}")
    String description
) {
}
