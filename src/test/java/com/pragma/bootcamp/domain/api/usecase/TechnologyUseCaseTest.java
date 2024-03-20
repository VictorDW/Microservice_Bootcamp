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


  @BeforeEach
  void setUp() {
  }

  @DisplayName("It should launch an exception in case a technology already exists.")
  @Test()
  void executeValidationTechnologyAlreadyExistTest() {

    //GIVEN
    Technology givenTechnology = new Technology(1L, "java", "Java con versión JDK 17");
    Technology expectedTechnology = new Technology(1L, "Java", "Java con versión JDK 17");
    String keyMessage = "error.already.exist.message";
    String responseMessage = "The Technology Java you want to create already exists";

    given(technologyPersistencePort.verifyByName(givenTechnology.getName()))
        .willReturn(Optional.of(expectedTechnology));

    given(messagePort.getMessage(keyMessage, "Technology", "Java")).willReturn(responseMessage);

    // Crear un spy de technologyUseCase
   // TechnologyUseCase technologyUseCaseSpy = Mockito.spy(technologyUseCase);

    //THEN
    assertThrows(TechnologyAlreadyExistException.class, ()-> technologyUseCase.executeValidationTechnologyAlreadyExist(givenTechnology));
   /*
   Method method = ReflectionUtils.findMethod(TechnologyUseCase.class, "executeValidationTechnologyAlready").orElse(null);
    //THEN

    if (Objects.nonNull(method)) {

      ReflectionUtils.makeAccessible(method);
      //TechnologyAlreadyExistException result = (TechnologyAlreadyExistException) method.invoke(technologyUseCase, givenTechnology);

      // Aquí puedes hacer tus aserciones
      assertThrows(TechnologyAlreadyExistException.class,() -> method.invoke(technologyUseCaseSpy, givenTechnology));
    }

    */
  }

  @Test
  void create() {

    
  }
}