package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.exception.NumberOutOfRangeException;
import com.pragma.bootcamp.domain.exception.RepeatedModelException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BootcampUseCaseTest {

  @InjectMocks
  private BootcampUseCase bootcampUseCase;
  @Mock
  IBootcampPersistencePort bootcampPersistencePort;
  @Mock
  IMessagePort messagePort;
  private Bootcamp givenBootcamp;
  private Bootcamp response;

  @BeforeEach
  void setUp() {
    this.givenBootcamp = new Bootcamp(1L, "Test", "Test");
    this.response = this.givenBootcamp;
  }

  @Test
  @DisplayName("Must throw an exception when a registered bootcamp with the same name is encountered")
  void test1() {

    //GIVEN
    Optional<Bootcamp> bootcampResponse = Optional.of(response);
    given(bootcampPersistencePort.verifyByName(any(String.class))).willReturn(bootcampResponse);
    given(messagePort.getMessage(
        DomainConstants.ALREADY_EXIST_MESSAGE,
        DomainConstants.Class.BOOTCAMP.getName(),
        "Test"
    )).willReturn(any(String.class));

    //WHEN - THAT
    assertThrows(AlreadyExistException.class, () -> bootcampUseCase.create(givenBootcamp));

  }

  @Test
  @DisplayName("Must allow to create a capacity")
  void test2() {

    //GIVEN

    given(bootcampPersistencePort.saveBootcamp(givenBootcamp)).willReturn(response);

    //WHEN
    Bootcamp result = bootcampUseCase.create(givenBootcamp);

    //THAT
    assertThat(result).isNotNull();
  }

}