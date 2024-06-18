package com.pragma.bootcamp.domain.util.orderby;

import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;

public enum CapacityOrderBy implements IOrderableProperty {

  NAME("name"),
  TECHNOLOGIES("technologies");

  private final String order;

  CapacityOrderBy(String order) {
    this.order = order;
  }

  @Override
  public String getOrderableProperty() {
    return order;
  }
}
