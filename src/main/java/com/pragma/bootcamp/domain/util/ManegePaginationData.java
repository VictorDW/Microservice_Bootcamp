package com.pragma.bootcamp.domain.util;

import java.util.Objects;


public final class ManegePaginationData {

  private static final Integer DEFAULT_PAGE = 0;
  private static final Integer DEFAULT_SIZE = 10;
  private static final Direction DEFAULT_DIRECTION = Direction.ASC;
  public static final String DEFAULT_ORDER_BY = OrderBy.NAME.name();

  private ManegePaginationData() { throw new IllegalStateException("utility class"); }

  private enum Direction {
    DESC,
    ASC
  }

  private enum OrderBy {
    NAME,
    TECHNOLOGY,
    CAPACITY
  }

  public static PaginationData definePaginationData(Integer page, Integer size, String direction) {
    return definePaginationData(page, size, direction, DEFAULT_ORDER_BY);
  }

  public static PaginationData definePaginationData(Integer page, Integer size, String direction, String order) {

    var temporaryPage = Objects.isNull(page) ? DEFAULT_PAGE : page;
    var temporarySize = Objects.isNull(size)  ? DEFAULT_SIZE : size;
    var temporaryOrder = Objects.isNull(direction)  ? DEFAULT_DIRECTION : defineDirection(direction.toUpperCase());
    var temporaryOrderBy = Objects.isNull(order) ? DEFAULT_ORDER_BY : defineOrderBy(order.toUpperCase()).toLowerCase();

    return new PaginationData(temporaryPage, temporarySize, temporaryOrder.name(), temporaryOrderBy);
  }

  private static Direction defineDirection(String order) {
    return (order.equals(Direction.DESC.name()) ?
            Direction.DESC : DEFAULT_DIRECTION);
  }

  private static String defineOrderBy(String order) {
    return (order.equals(OrderBy.TECHNOLOGY.name()) || order.equals(OrderBy.CAPACITY.name())) ?
            order : DEFAULT_ORDER_BY;
  }

}
