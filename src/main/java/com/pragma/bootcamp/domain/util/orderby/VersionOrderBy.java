package com.pragma.bootcamp.domain.util.orderby;

import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;

public enum VersionOrderBy implements IOrderableProperty {

  BOOTCAMP("bootcampEntity.name"),
  START_DATE("startDate"),
  MAXIMUM_CAPACITY("maximumCapacity");

  private final String order;

  VersionOrderBy(String order) {
    this.order = order;
  }

  @Override
  public String getOrderableProperty() {
    return order;
  }

}
