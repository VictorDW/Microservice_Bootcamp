package com.pragma.bootcamp.domain.util;

public final class DomainConstants {

  private DomainConstants() {
    throw new IllegalStateException("Utility class");
  }

  public enum Class {
    TECHNOLOGY("tecnologia", "tecnologias"),
    CAPACITY("capacidad", "capacidades"),
    BOOTCAMP("bootcamp", "bootcamps"),;
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
  public static final String ALREADY_BOOTCAMP_EXIST_MESSAGE = "error.bootcamp.already.exist.message";
  public static final String NUMBER_RANGE_MESSAGE =  "El número de %s que se pueden añadir a la %s debe estar entre %s y %s";
  public static final String NUMBER_RANGE_BOOTCAMP_MESSAGE =  "El número de %s que se pueden añadir al %s debe estar entre %s y %s";
  public static final String REPEATED_MODEL_MESSAGE = "La %s no debe tener %s repetidas";
  public static final String REPEATED_BOOTCAMP_MESSAGE = "El %s no debe tener %s repetidas";
  public static final String INVALID_DATE_EXCEPTION = "La fecha final debe ser posterior a la fecha de inicio";
}
