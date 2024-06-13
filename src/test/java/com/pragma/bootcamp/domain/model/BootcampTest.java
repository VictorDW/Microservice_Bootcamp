package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.NumberOutOfRangeException;
import com.pragma.bootcamp.domain.exception.RepeatedModelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BootcampTest {
  private static Stream<Arguments> provideListCapacityToValidateMinAndMaxQuantity() {
    return Stream.of(
        Arguments.of(List.of()),
        Arguments.of(
            Arrays.asList(
                new Capacity(1L, "Backend Java", "Java"),
                new Capacity(1L, "Backend Python", "Python"),
                new Capacity(1L, "Backend Java", "Java"),
                new Capacity(1L, "Backend Python", "Python"),
                new Capacity(1L, "Backend Java", "Java")
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("provideListCapacityToValidateMinAndMaxQuantity")
  @DisplayName("Should throw an exception when the number of capacities is less than one and greater than four.")
  void test1(List<Capacity> capacities) {

    //GIVEN
    Bootcamp bootcamp = new Bootcamp(1L, "FullStack Java/Angular", "Developer FullStack");

    assertThrows(NumberOutOfRangeException.class, () -> {
      bootcamp.setCapacityList(capacities);
    });
  }

  @ParameterizedTest
  @MethodSource("provideListCapacityToValidateMinAndMaxQuantity")
  @DisplayName("Given a number of capacities less than one and greater that four, must return the custom message of the exception.")
  void test2(List<Capacity> capacities) {

    //GIVEN
    Bootcamp bootcamp = new Bootcamp(1L, "FullStack Java/Angular", "Developer FullStack");
    String expectedMessage = "El número de capacidades que se pueden añadir al bootcamp debe estar entre "
        + Bootcamp.DEFAULT_MIN_NUMBER_CAPACITIES + " y " + Bootcamp.DEFAULT_MAX_NUMBER_CAPACITIES;

    try {
      //WHEN
      bootcamp.setCapacityList(capacities);
    }catch (NumberOutOfRangeException e) {
      //THAT
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  @Test
  @DisplayName("should throw an exception when the name of the capacities are repeated")
  void test3() {

    //GIVEN
    List<Capacity> capacities = List.of(
        new Capacity(1L, "Backend Java", "Java"),
        new Capacity(1L, "Backend Python", "Python"),
        new Capacity(1L, "Backend python", "Python")
    );
    Bootcamp bootcamp = new Bootcamp(1L, "FullStack Java/Angular", "Developer FullStack");

    //WHEN - THAT
    assertThrows(RepeatedModelException.class, () -> {
      bootcamp.setCapacityList(capacities);
    });
  }
}