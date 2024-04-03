package com.pragma.bootcamp.domain.model;

import com.pragma.bootcamp.domain.exception.InvalidDateException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VersionTest {
  @Test
  void test1() {
    //GIVEN
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.of(2024,4,1);

    //WHEN - THAT
    assertThrows(InvalidDateException.class, () -> new Version(null, "Test bootcamp", startDate, endDate,50));
  }
}
