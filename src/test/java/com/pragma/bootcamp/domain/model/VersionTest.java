package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.InvalidDateException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VersionTest {
  @Test
  void test1() {

    //GIVEN
    Bootcamp bootcamp = new Bootcamp(null, "Test", null);

    //WHEN - THAT
    assertThrows(InvalidDateException.class, () -> new Version(null, bootcamp, LocalDate.now(), LocalDate.of(2024,4,1), 20));
  }
}
