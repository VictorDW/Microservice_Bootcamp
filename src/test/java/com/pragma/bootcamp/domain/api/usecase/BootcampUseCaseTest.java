package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.IPaginationProvider;
import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.exception.NoDataFoundException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.spi.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.pagination.ManegePaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
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
        DomainConstants.ALREADY_BOOTCAMP_EXIST_MESSAGE,
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

 /* @Test
  @DisplayName("Should throw an exception when you get an empty list of capacities, and the message key must match the contents of the message.properties")
  void test3() {

    //GIVEN

    String keyMessage = "empty.list.message";
    PaginationData paginationData = ManegePaginationData.definePaginationData(1,10, "asc", "capacities");
    given(bootcampPersistencePort.getAll(paginationData)).willReturn(null);
    given(messagePort.getMessage(DomainConstants.EMPTY_LIST_MESSAGE)).willReturn(DomainConstants.EMPTY_LIST_MESSAGE);

    //WHEN - THAT

    assertAll(
        ()-> { assertThrows(NoDataFoundException.class, () -> bootcampUseCase.getAll(1,10,"asc","capacities"));},
        ()-> {
          try {
            bootcampUseCase.getAll(1,10,"asc","capacities");
          }catch (NoDataFoundException e) {
            assertEquals(keyMessage, e.getMessage());
          }
        }
    );
  } */

  @Test
  @DisplayName("Given some pagination data, it should return an instance of PaginationResponse containing the list and the pagination data")
  void test4() {

    //GIVEN
    List<Bootcamp> bootcamps = List.of(response);

    var paginationResponse = new PaginationResponse.Builder<Bootcamp>()
        .content(bootcamps)
        .isFirst(true)
        .isLast(true)
        .pageNumber(0)
        .pageSize(1)
        .totalElements(1L)
        .totalPages(1)
        .build();

    PaginationData paginationData = ManegePaginationData.definePaginationData(0,10, "asc", "capacities");
    given(bootcampPersistencePort.getAll(paginationData)).willReturn(paginationResponse);

    //WHEN
    var result = bootcampUseCase.getAll(0,10,"asc","capacities");

    //THAT
    assertThat(result.getContent()).isNotEmpty();
    assertThat(result.isFirst()).isTrue();
    assertThat(result.isLast()).isTrue();
    assertThat(result.getPageNumber()).isZero();
    assertThat(result.getPageSize()).isEqualTo(1);
    assertThat(result.getTotalElements()).isPositive();
    assertThat(result.getTotalPages()).isPositive();

  }
}