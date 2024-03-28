package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.NumberOutOfRangeException;
import com.pragma.bootcamp.domain.exception.RepeatedModelException;
import com.pragma.bootcamp.domain.util.DomainConstants;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;

@EqualsAndHashCode
public class Bootcamp {

  private final Long id;
  public static final Integer DEFAULT_MIN_NUMBER_CAPACITIES = 1;
  public static final Integer DEFAULT_MAX_NUMBER_CAPACITIES = 4;
  private final String name;
  private final String description;
  private final List<Capacity> capacityList;

  public Bootcamp(Long id, String name, String description, List<Capacity> capacityList) {

    executeValidationCapacitiesRange(capacityList);
    executeValidationNameCapacityRepeated(capacityList);

    this.id = id;
    this.name = name;
    this.description = description;
    this.capacityList = capacityList;
  }

  private void executeValidationCapacitiesRange(List<Capacity> capacityList) {

    int capacitySize = capacityList.size();

    if (capacitySize < DEFAULT_MIN_NUMBER_CAPACITIES || capacitySize > DEFAULT_MAX_NUMBER_CAPACITIES) {

      throw new NumberOutOfRangeException(
          String.format(
              DomainConstants.NUMBER_RANGE_MESSAGE,
              DomainConstants.Class.CAPACITY.getPluralName(),
              DomainConstants.Class.BOOTCAMP.getName(),
              DEFAULT_MIN_NUMBER_CAPACITIES,
              DEFAULT_MAX_NUMBER_CAPACITIES));
    }
  }

  private void executeValidationNameCapacityRepeated(List<Capacity> capacityList) {

    HashSet<String> uniqueName = new HashSet<>(capacityList.size());

    capacityList.forEach(capacity -> {
      if (!uniqueName.add(capacity.getName()))
        throw new RepeatedModelException(
            String.format(
                DomainConstants.REPEATED_MODEL_MESSAGE,
                DomainConstants.Class.BOOTCAMP.getName(),
                DomainConstants.Class.CAPACITY.getPluralName()));
    });

  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
  public List<Capacity> getCapacityList() {
    return capacityList;
  }
}
