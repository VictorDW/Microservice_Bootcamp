package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.NumberOutOfRangeException;
import com.pragma.bootcamp.domain.exception.RepeatedModelException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CapacityTest {

  private static Stream<Arguments> provideListTechnologyToValidateMinAndMaxQuantity() {
    return Stream.of(
        Arguments.of(
            Arrays.asList(
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python")
            )
        ),
        Arguments.of(
            Arrays.asList(
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python"),
                new Technology(1L, "Java", "Java 17"),
                new Technology(2L, "Python", "Python")
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("provideListTechnologyToValidateMinAndMaxQuantity")
  @DisplayName("Should throw an exception when the number of technologies is less than three and greater than twenty.")
  void test1(List<Technology> technologies) {

    assertThrows(NumberOutOfRangeException.class, () -> {
      new Capacity(1L, "Backend Java", "Developer backend java", technologies);
    });
  }

  @ParameterizedTest
  @MethodSource("provideListTechnologyToValidateMinAndMaxQuantity")
  @DisplayName("Given a number of technologies less than three, you must return the custom message of the exception.")
  void test2(List<Technology> technologies) {

     String expectedMessage = "The number of Technologies that can be added to the Capacity most be between "
         + Capacity.DEFAULT_MIN_NUMBER_TECHNOLOGIES + " and " + Capacity.DEFAULT_MAX_NUMBER_TECHNOLOGIES;
    //GIVEN

    try {
       new Capacity(1L, "Backend Java", "Developer backend java", technologies);
       //WHEN
    }catch (NumberOutOfRangeException e) {
      assertEquals(expectedMessage, e.getMessage());
      //THAT
    }
  }

  @Test
  @DisplayName("should throw an exception when the name of the technologies are repeated")
  void test3() {

    List<Technology> technologies =  Arrays.asList(
        new Technology(1L, "Java", "Java 17"),
        new Technology(2L, "Python", "Python"),
        new Technology(2L, "Python", "Python")
    );

    assertThrows(RepeatedModelException.class, () -> {
      new Capacity(1L, "Backend Java", "Developer backend java", technologies);
    });
  }

}