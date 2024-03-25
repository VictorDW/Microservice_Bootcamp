package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;

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
    private IMessagePort messagePort;
    private List<Technology> technologies;
    private Capacity givenCapacity;
    private Capacity response;

    @BeforeEach
    void setUp() {
        this.technologies = List.of(
                new Technology(null, "Java", null),
                new Technology(null, "Python", null),
                new Technology(null, "Javascript", null)
        );
        this.givenCapacity = new Capacity(1L, "Backend Java", "Java Backend Developer", technologies);
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
        assertThrows(AlreadyExistException.class, ()->  capacityUseCase.create(givenCapacity));
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
}