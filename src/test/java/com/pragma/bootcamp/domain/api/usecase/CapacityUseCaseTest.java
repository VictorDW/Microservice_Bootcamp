package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.exception.NoEntityFoundException;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CapacityUseCaseTest {

    @InjectMocks
    private CapacityUseCase capacityUseCase;

    @Mock
    private ICapacityPersistencePort capacityPersistencePort;

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @Mock
    private IMessagePort messagePort;

    private Capacity givenCapacity;
    private Capacity response;

    @BeforeEach
    void setUp() {
        List<Technology> technologies = List.of(
            new Technology(null, "Java", null),
            new Technology(null, "Python", null),
            new Technology(null, "Javascript", null)
        );
        this.givenCapacity = new Capacity(1L, "Backend Java", "Java Backend Developer");
        givenCapacity.setTechnologyList(technologies);
        this.response = this.givenCapacity;
    }

    @Test
    @DisplayName("Must throw an exception when a registered capability with the same name is encountered")
    void test1() {

        //GIVEN

        Optional<Capacity> capacityResponse = Optional.of(response);
        given(capacityPersistencePort.verifyByName(any(String.class))).willReturn(capacityResponse);
        given(messagePort.getMessage(
            DomainConstants.ALREADY_EXIST_MESSAGE,
            DomainConstants.Class.CAPACITY.getName(),
            "Backend Java")
        ).willReturn(any(String.class));

        //WHEN - THAT
        assertThrows(AlreadyExistException.class, () -> capacityUseCase.create(givenCapacity));
    }

    @Test
    @DisplayName("Must allow to create a capacity")
    void test2() {

        //GIVEN
        given(technologyPersistencePort.verifyByName(any(String.class)))
            .willAnswer(invocation -> {
                String technologyName = invocation.getArgument(0);
                return Optional.of(new Technology(1L, technologyName, null));
            });
        given(capacityPersistencePort.saveCapacity(any(Capacity.class))).willReturn(response);

        Capacity result = capacityUseCase.create(givenCapacity);
        //WHEN - THAT
        assertThat(result).isNotNull();
    }



 @Test
  @DisplayName("Should throw an exception when trying to create a capacity with a non-existing technology, and the message key must match the contents of the message.properties")
  void test3() {

      //GIVEN
     String nameTechnology = givenCapacity.getTechnologyList().get(0).getName();
     String keyMessage = "error.not.found.technology.message";
     given(technologyPersistencePort.verifyByName(any(String.class))).willReturn(Optional.empty());
     given(messagePort.getMessage(DomainConstants.NOT_FOUND_TECHNOLOGY_MESSAGE, nameTechnology)).willReturn(keyMessage);


      //WHEN - THAT
      assertAll(
          "Grouped Assertions de CapacityUseCase",
          () -> assertThrows(NoEntityFoundException.class, () -> capacityUseCase.create(givenCapacity)),
          ()-> {
              try {
                  capacityUseCase.create(givenCapacity);
              }catch(NoEntityFoundException e) {
                  assertEquals(keyMessage, e.getMessage(), "The key Message should be error.not.found.technology.message");
              }
          }
      );
  }

    @Test
    @DisplayName("Given some pagination data, it should return an instance of PaginationResponse containing the list of capacities and the pagination data")
    void test4() {

        //GIVEN
        var paginationResponse = new PaginationResponse.Builder<Capacity>()
            .content(List.of(response))
            .isFirst(true)
            .isLast(true)
            .pageNumber(0)
            .pageSize(1)
            .totalElements(1L)
            .totalPages(1)
            .build();

        PaginationData paginationData = ManegePaginationData.definePaginationData(0, 10, "DESC", "name");
        given(capacityPersistencePort.getAllCapacity(paginationData)).willReturn(paginationResponse);

        //THAT
        var result = capacityUseCase.getAll(0, 10, "DESC", "name");

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