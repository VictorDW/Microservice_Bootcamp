package com.pragma.bootcamp.domain.util;

import com.pragma.bootcamp.domain.util.order.IOrderBy;

import java.util.Objects;
import java.util.Optional;


public final class ManegePaginationData {

  private static final Integer DEFAULT_PAGE = 0;
  private static final Integer DEFAULT_SIZE = 10;
  private static final Direction DEFAULT_DIRECTION = Direction.ASC;

  private ManegePaginationData() { throw new IllegalStateException("utility class"); }

  private enum Direction {
    DESC,
    ASC
  }

  public static PaginationData definePaginationData(Integer page, Integer size, String direction, String orderBy) {

    var temporaryPage = Objects.isNull(page) ? DEFAULT_PAGE : page;
    var temporarySize = Objects.isNull(size)  ? DEFAULT_SIZE : size;
    var temporaryDirection = Objects.isNull(direction)  ? DEFAULT_DIRECTION : defineDirection(direction);

    return new PaginationData(temporaryPage, temporarySize, temporaryDirection.name(), orderBy);
  }

  private static Direction defineDirection(String direction) {
    return (direction.equalsIgnoreCase(Direction.DESC.name()) ?
            Direction.DESC : DEFAULT_DIRECTION);
  }

  public static <E extends Enum<E>> String defineOrderBy(Class<E> enumClass, IOrderBy orderByDefault, String propertyToSearch) {

    if(Objects.isNull(propertyToSearch)) {
      return orderByDefault.getOrderBy();
    }

    return getProperty(enumClass, propertyToSearch)
          .map(propertyEnum -> (IOrderBy) propertyEnum)
          .orElse(orderByDefault)
          .getOrderBy();
  }
  private static <E extends Enum<E>> Optional<E> getProperty(Class<E> enumClass, String propertyToSearch) {
    try {
      return Optional.of(Enum.valueOf(enumClass, propertyToSearch.toUpperCase()));
    }catch (IllegalArgumentException exception) {
      return Optional.empty();
    }
  }

}
