package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.exception.NoDataFoundException;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;

import com.pragma.bootcamp.domain.util.order.ManegePaginationData;
import com.pragma.bootcamp.domain.util.order.PaginationData;
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
class CapacityUseCaseTest {

    @InjectMocks
    private CapacityUseCase capacityUseCase;
    @Mock
    private ICapacityPersistencePort capacityPersistencePort;
    @Mock
    private IMessagePort messagePort;
    private Capacity givenCapacity;
    private Capacity response;

    @BeforeEach
    void setUp() {
        this.givenCapacity = new Capacity(1L, "Backend Java", "Java Backend Developer");
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
        given(capacityPersistencePort.saveCapacity(any(Capacity.class))).willReturn(response);

        Capacity result = capacityUseCase.create(givenCapacity);
        //WHEN - THAT
        assertThat(result).isNotNull();
    }

  @Test
  @DisplayName("Should throw an exception when you get an empty list of capacities, and the message key must match the contents of the message.properties")
  void test3() {

      //GIVEN
      String keyMessage = "empty.list.message";
      PaginationData paginationData = ManegePaginationData.definePaginationData(0, 10, "DESC", "name");
      given(capacityPersistencePort.getAllCapacity(paginationData)).willReturn(new ArrayList<>());
      given(messagePort.getMessage(DomainConstants.EMPTY_LIST_MESSAGE)).willReturn(DomainConstants.EMPTY_LIST_MESSAGE);

      //WHEN - THAT
      assertAll(
          "Grouped Assertions de CapacityUseCase",
          () -> assertThrows(NoDataFoundException.class, () -> capacityUseCase.getAll(0, 10, "DESC", "name")),
          ()-> {
              try {
                  capacityUseCase.getAll(0, 10, "DESC", "name");
              }catch(NoDataFoundException e) {
                  assertEquals(keyMessage, e.getMessage(), "The key Message should be empty.list.message");
              }
          }
      );
  }

    @Test
    @DisplayName("Given some paging data, it should return a list of capacities")
    void test4() {

        //GIVEN
        List<Capacity> capacities = List.of(response);
        PaginationData paginationData = ManegePaginationData.definePaginationData(0, 10, "DESC", "name");
        given(capacityPersistencePort.getAllCapacity(paginationData)).willReturn(capacities);

        //THAT
        List<Capacity> result = capacityUseCase.getAll(0, 10, "DESC", "name");

        //THAT
        assertThat(result).isNotEmpty();
    }
}