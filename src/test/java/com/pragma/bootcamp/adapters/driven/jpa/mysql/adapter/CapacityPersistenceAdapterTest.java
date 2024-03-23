package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

  @Test
  @DisplayName("should throw an exception when trying to create a capacity with a non-existing technology")
  void test1() {

    //GIVEN
    List<Technology> technologies = List.of(
        new Technology(null, "Java", null),
        new Technology(null, "Python", null),
        new Technology(null, "Javascript", null)
    );

    String nameTechnology = technologies.get(0).getName();
    CapacityEntity capacityEntity = new CapacityEntity();

    Capacity givenCapacity = new Capacity(1L, "Backend Java", "Java Backend Developer", technologies);
    given(capacityEntityMapper.modelToEntity(givenCapacity)).willReturn(capacityEntity);
    given(technologyRepository.findByNameIgnoreCase(nameTechnology)).willReturn(Optional.empty());
    given(messagePort.getMessage(Constants.NOT_FOUND_TECHNOLOGY_MESSAGE,nameTechnology)).willReturn(any(String.class));
    //WHEN - THAT
    assertThrows(NotFoundException.class, ()-> capacityPersistenceAdapter.saveCapacity(givenCapacity));

  }
}