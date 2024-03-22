package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.NoDataFoundException;
import com.pragma.bootcamp.domain.exception.TechnologyAlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.ManegePaginationData;
import com.pragma.bootcamp.domain.util.PaginationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
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

  @Test
  @DisplayName("Must throw an exception when you want to get a list of technologies and it is empty. ")
  void test3() {

    //GIVEN

    Integer page = 2;
    Integer size = 4;
    String order = "DESC";
    String messageException = DomainConstants.EMPTY_LIST_MESSAGE;
    String messageResponse = "No result found";

    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, order);
    given(technologyPersistencePort.getAllTechnology(paginationData)).willReturn(new ArrayList<>());
    given(messagePort.getMessage(messageException)).willReturn(messageResponse);

    //WHEN - THAT
    assertThrows(NoDataFoundException.class, () -> {
      technologyUseCase.getAll(page, size, order);
    });
  }

  @Test
  @DisplayName("Given paging data should return a list of technologies")
  void test4() {

    //GIVEN

    Integer page = 2;
    Integer size = 4;
    String order = "DESC";

    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, order);
    given(technologyPersistencePort.getAllTechnology(paginationData))
            .willReturn(List.of(new Technology(1L, "Java", "JDK")));

    //WHEN

    List<Technology> resul = technologyUseCase.getAll(page, size, order);

    //THAT
    assertThat(resul).isNotEmpty();
  }
}