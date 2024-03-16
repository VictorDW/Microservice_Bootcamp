package com.pragma.bootcamp.domain.util;

import java.util.Objects;


public final class ManegePaginationData {

  private static final Integer DEFAULT_PAGE = 0;
  private static final Integer DEFAULT_SIZE = 10;
  private static final Direction DEFAULT_DIRECTION = Direction.ASC;
  public static final String DEFAULT_ORDER = "name";

  private ManegePaginationData() { throw new IllegalStateException("utility class"); }

  public enum Direction {
    DESC,
    ASC
  }

  public static PaginationData definePaginationData(Integer page, Integer size, String order) {
    return definePaginationData(page, size, order, DEFAULT_ORDER);
  }

  public static PaginationData definePaginationData(Integer page, Integer size, String order, String orderBy) {

    var temporaryPage = Objects.isNull(page) ? DEFAULT_PAGE : page;
    var temporarySize = Objects.isNull(size)  ? DEFAULT_SIZE : size;
    var temporaryOrder = Objects.isNull(order)  ? DEFAULT_DIRECTION : validateDirection(order);
    var temporaryOrderBy = Objects.isNull(orderBy) ? DEFAULT_ORDER : orderBy;

    return new PaginationData(temporaryPage, temporarySize, temporaryOrder.name(), temporaryOrderBy);
  }

  private static Direction validateDirection(String order) {
    return (order.toUpperCase().equals(Direction.DESC.name()) ? Direction.DESC : DEFAULT_DIRECTION);
  }
}
