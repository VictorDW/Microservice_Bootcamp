package com.pragma.bootcamp.domain.util.orderby;

import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;

public enum BootcampOrderBy implements IOrderableProperty {

  NAME("name"),
  CAPACITIES("capacities");

  private final String order;

  BootcampOrderBy(String order) {
    this.order = order;
  }

  @Override
  public String getOrderableProperty() {
    return order;
  }

}
