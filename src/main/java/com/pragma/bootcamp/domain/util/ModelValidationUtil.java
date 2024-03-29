package com.pragma.bootcamp.domain.util;

import com.pragma.bootcamp.domain.exception.NumberOutOfRangeException;
import com.pragma.bootcamp.domain.exception.RepeatedModelException;
import com.pragma.bootcamp.domain.model.ParentModel;

import java.util.HashSet;
import java.util.List;

public class ModelValidationUtil {

  private ModelValidationUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static void executeValidationModel(List<? extends ParentModel> list, String pluralModelName, String parentModelName, Integer min, Integer max) {
    executeValidationModelRange(list, pluralModelName, parentModelName, min, max);
    executeValidationNameModelRepeated(list, pluralModelName, parentModelName);
  }

  private static void executeValidationModelRange(List<? extends ParentModel> list, String pluralModelName, String parentModelName, Integer min, Integer max) {

    int size = list.size();

    if (size < min || size > max) {
      throw new NumberOutOfRangeException(
          String.format(
              DomainConstants.NUMBER_RANGE_MESSAGE,
              pluralModelName,
              parentModelName,
              min,
              max));
    }
  }

  private static void executeValidationNameModelRepeated(List<? extends ParentModel> list, String pluralModelName, String parentModelName ) {

    HashSet<String> uniqueName = new HashSet<>(list.size());

    list.forEach(model -> {
      if (!uniqueName.add(model.getName()))
        throw new RepeatedModelException(
            String.format(
                DomainConstants.REPEATED_MODEL_MESSAGE,
                parentModelName,
                pluralModelName));
    });

  }
}
