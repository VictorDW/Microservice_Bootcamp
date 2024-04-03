package com.pragma.bootcamp.adapters.driving.http.dto.request;

import com.pragma.bootcamp.adapters.util.FormatDate;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;

public record AddVersionRequest(
    String bootcampName,

    @FutureOrPresent
    @FormatDate
    LocalDate endDate,
    @FutureOrPresent
    @FormatDate
    LocalDate startDate,
    Integer maximumCapacity
) { }
