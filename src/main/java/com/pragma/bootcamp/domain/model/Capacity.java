package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class Capacity extends ParentModel {
    public static final  Integer DEFAULT_MIN_NUMBER_TECHNOLOGIES = 3;
    public static final Integer DEFAULT_MAX_NUMBER_TECHNOLOGIES = 20;
    private List<Technology> technologyList = new ArrayList<>();

  public void addTechnologyList(List<Technology> technologyList) {

    ModelValidationUtil.executeValidationModel(
        technologyList,
        DomainConstants.Class.TECHNOLOGY.getPluralName(),
        DomainConstants.Class.CAPACITY.getName(),
        DEFAULT_MIN_NUMBER_TECHNOLOGIES,
        DEFAULT_MAX_NUMBER_TECHNOLOGIES);

    this.technologyList = technologyList;
  }

  public Capacity(Long id, String name, String description) {
      super(id, name, description);
    }

    public List<Technology> getTechnologyList() {
        return technologyList;
    }
}
