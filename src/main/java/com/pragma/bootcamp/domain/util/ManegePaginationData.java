package com.pragma.bootcamp.domain.util;

import java.util.Objects;


public final class ManegePaginationData {

  private static final Integer DEFAULT_PAGE = 0;
  private static final Integer DEFAULT_SIZE = 10;
  private static final Direction DEFAULT_DIRECTION = Direction.ASC;
  public static final String DEFAULT_PROPERTY = Properties.NAME.name();

  private ManegePaginationData() { throw new IllegalStateException("utility class"); }

  private enum Direction {
    DESC,
    ASC
  }

  private enum Properties {
    NAME,
    TECHNOLOGIES,
    CAPACITIES
  }

  public static PaginationData definePaginationData(Integer page, Integer size, String direction) {
    return definePaginationData(page, size, direction, DEFAULT_PROPERTY);
  }

  public static PaginationData definePaginationData(Integer page, Integer size, String direction, String property) {

    var temporaryPage = Objects.isNull(page) ? DEFAULT_PAGE : page;
    var temporarySize = Objects.isNull(size)  ? DEFAULT_SIZE : size;
    var temporaryDirection = Objects.isNull(direction)  ? DEFAULT_DIRECTION : defineDirection(direction);
    var temporaryOrderBy = Objects.isNull(property) ? DEFAULT_PROPERTY : defineOrderBy(property);

    return new PaginationData(temporaryPage, temporarySize, temporaryDirection.name(), temporaryOrderBy.toLowerCase());
  }

  private static Direction defineDirection(String direction) {
    return (direction.equalsIgnoreCase(Direction.DESC.name()) ?
            Direction.DESC : DEFAULT_DIRECTION);
  }

  private static String defineOrderBy(String property) {
    return (property.equalsIgnoreCase(Properties.TECHNOLOGIES.name()) || property.equalsIgnoreCase(Properties.CAPACITIES.name())) ?
            property : DEFAULT_PROPERTY;
  }

}
