package com.pragma.bootcamp.domain.util;

import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.exception.NumberOutOfRangeException;
import com.pragma.bootcamp.domain.exception.RepeatedModelException;
import com.pragma.bootcamp.domain.model.ParentModel;
import com.pragma.bootcamp.domain.spi.IMessagePort;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class ModelValidationUtil {

  private ModelValidationUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static void  executeValidationModel(List<? extends ParentModel> list, String pluralModelName, String parentModelName, Integer min, Integer max) {
    validationModelRange(list, pluralModelName, parentModelName, min, max);
    validationNameModelRepeated(list, pluralModelName, parentModelName);
  }

  private static <T> void validationModelRange(List<T> list, String pluralModelName, String parentModelName, Integer min, Integer max) {

    int size = list.size();

    if (size < min || size > max) {
      throw new NumberOutOfRangeException(
          String.format(
              DomainConstants.Class.BOOTCAMP.getName().equals(parentModelName) ?
                  DomainConstants.NUMBER_RANGE_BOOTCAMP_MESSAGE :
                  DomainConstants.NUMBER_RANGE_MESSAGE,
              pluralModelName,
              parentModelName,
              min,
              max));
    }
  }

  private static <T extends ParentModel> void validationNameModelRepeated(List<T> list, String pluralModelName, String parentModelName ) {

    HashSet<String> uniqueName = new HashSet<>(list.size());

    list.forEach(model -> {
      if (!uniqueName.add(model.getName().toLowerCase()))
        throw new RepeatedModelException(
            String.format(
                DomainConstants.Class.BOOTCAMP.getName().equals(parentModelName) ?
                    DomainConstants.REPEATED_BOOTCAMP_MESSAGE :
                    DomainConstants.REPEATED_MODEL_MESSAGE,
                parentModelName,
                pluralModelName));
    });
  }

  public static <T extends ParentModel> void validationModelAlreadyExist(Optional<T> checkModel, String modelName, IMessagePort messagePort) {

    checkModel.ifPresent(
        existModel -> {
          throw new AlreadyExistException(
              messagePort.getMessage(
                  DomainConstants.Class.BOOTCAMP.getName().equals(modelName) ?
                      DomainConstants.ALREADY_BOOTCAMP_EXIST_MESSAGE :
                      DomainConstants.ALREADY_EXIST_MESSAGE,
                  modelName,
                  existModel.getName()));
        });
  }
}
