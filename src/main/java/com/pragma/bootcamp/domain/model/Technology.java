package com.pragma.bootcamp.domain.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class Technology extends ParentModel {

  public Technology(Long id, String name, String description) {
    super(id, name, description);
  }

  public enum OrderBy {
    NAME("name");

    private final String order;

    OrderBy(String order) {
      this.order = order;
    }
    public String getOrderBy() {
      return order;
    }
  }

}
