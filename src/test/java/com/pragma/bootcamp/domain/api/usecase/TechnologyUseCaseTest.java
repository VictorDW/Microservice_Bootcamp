package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.TechnologyAlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {

  @Mock
  private ITechnologyPersistencePort technologyPersistencePort;
  @Mock
  private IMessagePort messagePort;
  @InjectMocks
  private TechnologyUseCase technologyUseCase;
  private Technology givenTechnology;
  private Technology expectedTechnology;


  @BeforeEach
  void setUp() {
    givenTechnology = new Technology(1L, "Java", "Java con versión JDK 17");
    expectedTechnology = new Technology(1L, "Java", "Java con versión JDK 17");
  }

  @DisplayName("given a technology should throw an exception since it is already registered")
  @Test
  void test1() {

    //GIVEN
    String keyMessage = "error.already.exist.message";
    String responseMessage = "The Technology Java you want to create already exists";

    given(technologyPersistencePort.verifyByName("Java"))
        .willReturn(Optional.of(expectedTechnology));

    given(messagePort.getMessage(keyMessage, "Technology", "Java")).willReturn(responseMessage);


    //THAT
    assertThrows(TechnologyAlreadyExistException.class, ()-> technologyUseCase.create(givenTechnology));

  }

  @DisplayName("Given a technology should allow to create it")
  @Test
  void test2() {

    //GIVEN
   /* given(technologyPersistencePort.verifyByName("Ruby"))
        .willReturn(Optional.empty());*/

    given(technologyPersistencePort.saveTechnology(givenTechnology)).willReturn(expectedTechnology);

    //WHEN
     Technology result = technologyUseCase.create(givenTechnology);

    //THAT
    assertEquals(expectedTechnology, result);
  }
}