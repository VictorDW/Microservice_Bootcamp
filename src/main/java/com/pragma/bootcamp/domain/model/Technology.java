package com.pragma.bootcamp.domain.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class Technology extends ParentModel {

  public Technology(Long id, String name, String description) {
    super(id, name, description);
  }
}
