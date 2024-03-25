package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoEntityFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.IMessagePort;
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
class CapacityPersistenceAdapterTest {

  @InjectMocks
  private CapacityPersistenceAdapter capacityPersistenceAdapter;
  @Mock
  private  ICapacityEntityMapper capacityEntityMapper;
  @Mock
  private  ITechnologyRepository technologyRepository;
  @Mock
  private  ICapacityRepository capacityRepository;
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
  @DisplayName("should throw an exception when trying to create a capacity with a non-existing technology")
  void test1() {

    //GIVEN
    String nameTechnology = technologies.get(0).getName();
    CapacityEntity capacityEntity = new CapacityEntity();

    given(capacityEntityMapper.modelToEntity(givenCapacity)).willReturn(capacityEntity);
    given(technologyRepository.findByNameIgnoreCase(nameTechnology)).willReturn(Optional.empty());
    given(messagePort.getMessage(Constants.NOT_FOUND_TECHNOLOGY_MESSAGE,nameTechnology)).willReturn(any(String.class));
    //WHEN - THAT
    assertThrows(NoEntityFoundException.class, ()-> capacityPersistenceAdapter.saveCapacity(givenCapacity));
  }

  @Test
  @DisplayName("Must allow for the creation of a capacity")
  void test2() {

    //GIVEN
    CapacityEntity capacityEntity = new CapacityEntity();
    TechnologyEntity technologyEntity = new TechnologyEntity();

    given(capacityEntityMapper.modelToEntity(givenCapacity)).willReturn(capacityEntity);
    given(technologyRepository.findByNameIgnoreCase(any(String.class))).willReturn(Optional.of(technologyEntity));
    given(capacityRepository.save(capacityEntity)).willReturn(capacityEntity);
    given(capacityEntityMapper.entityToModel(capacityEntity)).willReturn(response);

    //WHEN
    Capacity result = capacityPersistenceAdapter.saveCapacity(givenCapacity);
    //THAT
    assertThat(result).isNotNull();
  }


  @Test
  @DisplayName("Given the name of a capability, it must return an Optional with the capability found")
  void test3() {

    //GIVEN
    String name = "Java";
    CapacityEntity capacityEntity = new CapacityEntity();

    given(capacityRepository.findByNameIgnoreCase(name)).willReturn(Optional.of(capacityEntity));
    given(capacityEntityMapper.entityToModel(capacityEntity))
            .willReturn(response);

    //WHEN
    var result = capacityPersistenceAdapter.verifyByName(name);

    //THAT
    assertThat(result).isNotEmpty();
  }

  @Test
  @DisplayName("Given the name of a capability, it must return an empty optional if the capability does not exist")
  void test4() {

    //GIVEN
    String name = "Java";
    given(capacityRepository.findByNameIgnoreCase(name)).willReturn(Optional.empty());

    //WHEN
    var result = capacityPersistenceAdapter.verifyByName(name);

    //THAT
    assertThat(result).isEmpty();
  }
}