package com.pragma.bootcamp.domain.util;

public enum DomainClass {
  TECHNOLOGY("Technology");
  private final String name;

  DomainClass(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
