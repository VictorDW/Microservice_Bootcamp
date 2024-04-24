package com.pragma.bootcamp.domain.model;


public class ParentModel {

  private final Long id;
  private final String name;
  private final String description;

  protected ParentModel(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
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
}
