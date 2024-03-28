package com.pragma.bootcamp.domain.util;

import com.pragma.bootcamp.domain.exception.NumberOutOfRangeException;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;

import java.util.List;

public class ModelValidationUtil {

  public ModelValidationUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static void executeValidationModelRange(List<?> list, String pluralModelName, String parentModelName, Integer min, Integer max) {

    int technologySize = list.size();

    if (technologySize < min || technologySize > max) {

      throw new NumberOutOfRangeException(
          String.format(
              DomainConstants.NUMBER_RANGE_MESSAGE,
              pluralModelName,
              parentModelName,
              min,
              max));
    }
  }
}
