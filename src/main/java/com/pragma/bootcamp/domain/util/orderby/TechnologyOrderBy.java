package com.pragma.bootcamp.domain.util.orderby;

import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;

public enum TechnologyOrderBy implements IOrderableProperty {

  NAME("name");

  private final String order;

  TechnologyOrderBy(String order) {
    this.order = order;
  }

  @Override
  public String getOrderableProperty() {
    return order;
  }
}
