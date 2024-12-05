package com.pragma.bootcamp.domain.util;

public final class DomainConstants {

  private DomainConstants() {
    throw new IllegalStateException("Utility class");
  }

  public enum Class {
    TECHNOLOGY("Technology", "Technologies"),
    CAPACITY("Capacity", "Capacities"),
    BOOTCAMP("Bootcamp", "Bootcamps");
    private final String name;
    private final String pluralName;

    Class(String name, String pluralName) {
      this.name = name;
      this.pluralName = pluralName;
    }

    public String getName() {
      return name;
    }
    public String getPluralName() {
      return this.pluralName;
    }
  }

  public static final String EMPTY_LIST_MESSAGE = "empty.list.message";
  public static final String ALREADY_EXIST_MESSAGE = "error.already.exist.message";
  public static final String NUMBER_RANGE_MESSAGE =  "The number of %s that can be added to the %s most be between %s and %s";
  public static final String REPEATED_MODEL_MESSAGE = "The %s must not have repeated %s";
  public static final String INVALID_DATE_EXCEPTION = "The end date must be later than the start date";
}
