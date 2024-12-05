package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.InvalidDateException;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;

import java.time.LocalDate;

public class Version {

  private final Long id;
  private final String bootcampName;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final Integer maximumCapacity;

  public Version(Long id, String bootcampName, LocalDate startDate, LocalDate endDate, Integer maximumCapacity) {
    executeValidateDate(startDate, endDate);
    this.id = id;
    this.bootcampName = bootcampName;
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

  public String getBootcampName() {
    return bootcampName;
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
