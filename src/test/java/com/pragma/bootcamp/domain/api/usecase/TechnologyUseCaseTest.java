package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

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

  @DisplayName("Given a technology should throw an exception since it is already registered")
  @Test
  void test1() {

    //GIVEN
    String keyMessage = "error.already.exist.message";
    String responseMessage = "The Technology Java you want to create already exists";

    given(technologyPersistencePort.verifyByName("Java"))
        .willReturn(Optional.of(expectedTechnology));

    given(messagePort.getMessage(keyMessage, DomainConstants.Class.TECHNOLOGY.getName(), "Java")).willReturn(responseMessage);


    //THAT
    assertThrows(AlreadyExistException.class, ()-> technologyUseCase.create(givenTechnology));

  }

  @DisplayName("Given a technology should allow to create it")
  @Test
  void test2() {

    //GIVEN

    given(technologyPersistencePort.saveTechnology(givenTechnology)).willReturn(expectedTechnology);

    //WHEN
     Technology result = technologyUseCase.create(givenTechnology);

    //THAT
    assertEquals(expectedTechnology, result);
  }
}