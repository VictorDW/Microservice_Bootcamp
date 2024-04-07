package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.util.order.IOrderBy;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class Technology extends ParentModel {

  public Technology(Long id, String name, String description) {
    super(id, name, description);
  }

  public enum OrderBy implements IOrderBy {
    NAME("name");
    
    private final String order;
    OrderBy(String order) {
      this.order = order;
    }
    @Override
    public String getOrderBy() {
      return order;
    }
  }

}
