package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.InvalidDateException;
import com.pragma.bootcamp.domain.util.DomainConstants;

import java.time.LocalDate;

public class Version {

  private final Long id;
  private final Bootcamp bootcamp;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final Integer maximumCapacity;

  public Version(Long id, Bootcamp bootcamp, LocalDate startDate, LocalDate endDate, Integer maximumCapacity) {
    executeValidateDate(startDate, endDate);
    this.id = id;
    this.bootcamp = bootcamp;
    this.startDate = startDate;
    this.endDate = endDate;
    this.maximumCapacity = maximumCapacity;
  }

  private void executeValidateDate(LocalDate startDate, LocalDate endDate) {
    if (endDate.isBefore(startDate)) {
      throw new InvalidDateException(DomainConstants.INVALID_DATE_EXCEPTION);
    }
  }

  public Long getId() {
    return id;
  }

  public Bootcamp getBootcamp() {
    return bootcamp;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public Integer getMaximumCapacity() {
    return maximumCapacity;
  }
}
