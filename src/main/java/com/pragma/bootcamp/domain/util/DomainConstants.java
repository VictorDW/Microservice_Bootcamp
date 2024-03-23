package com.pragma.bootcamp.domain.util;

public final class DomainConstants {

  private DomainConstants() {
    throw new IllegalStateException("Utility class");
  }

  public enum Class {
    TECHNOLOGY("Technology"),
    CAPACITY("Capacity");
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
  public static final String NUMBER_TECHNOLOGIES_MIN_MESSAGE = "The minimum of technologies that can be added to the capacity is %s";
  public static final String NUMBER_TECHNOLOGIES_MAX_MESSAGE = "The maximum number of technologies that can be added to the capacity is %s";
  public static final String REPEATED_TECHNOLOGY_MESSAGE = "The capacity must not have repeated technologies";
}
