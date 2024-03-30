package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class Bootcamp extends ParentModel {

  public static final Integer DEFAULT_MIN_NUMBER_CAPACITIES = 1;
  public static final Integer DEFAULT_MAX_NUMBER_CAPACITIES = 4;

  private List<Capacity> capacityList;

  public Bootcamp(Long id, String name, String description) {
    super(id, name, description);
    this.capacityList = new ArrayList<>();
  }

  public void setCapacityList(List<Capacity> capacityList) {

    ModelValidationUtil.executeValidationModel(
        capacityList,
        DomainConstants.Class.CAPACITY.getPluralName(),
        DomainConstants.Class.BOOTCAMP.getName(),
        DEFAULT_MIN_NUMBER_CAPACITIES,
        DEFAULT_MAX_NUMBER_CAPACITIES);

    this.capacityList = capacityList;
  }

  public List<Capacity> getCapacityList() {
    return capacityList;
  }
}
