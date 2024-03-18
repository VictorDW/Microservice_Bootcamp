package com.pragma.bootcamp.domain.util;

public final class DomainConstants {

  private DomainConstants() {
    throw new IllegalStateException("Utility class");
  }

  public enum Class {
    TECHNOLOGY("Technology");
    private final String name;

    Class(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  public static final String EMPTY_LIST_MESSAGE = "empty.list.message";
  public static final String ALREADY_EXIST_MESSAGE = "error.already.exist.message";
}
