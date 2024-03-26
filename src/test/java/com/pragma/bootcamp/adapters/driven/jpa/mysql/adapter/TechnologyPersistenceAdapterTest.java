package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.model.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TechnologyPersistenceAdapterTest {

  @Mock
  private ITechnologyEntityMapper technologyEntityMapper;
  @Mock
  private ITechnologyRepository technologyRepository;
  @InjectMocks
  private TechnologyPersistenceSimple technologyPersistenceAdapter;
  private String name;

  @BeforeEach
  void setUp() {
    name = "Java";
  }

  @DisplayName("Given a technology you should insert it in the DB")
  @Test
  void test1() {

    //GIVEN
    Technology giveTechnology = new Technology(null, "Java", "Java con versión JDK 17");
    Technology expectedTechnology = new Technology(1L, "Java", "Java con versión JDK 17");
    TechnologyEntity technologyEntity = new TechnologyEntity(1L, "Java", "Java con versión JDK 17", new ArrayList<>() {
    });

    given(technologyEntityMapper.modelToEntity(giveTechnology)).willReturn(technologyEntity);
    given(technologyRepository.save(technologyEntity)).willReturn(technologyEntity);
    given(technologyEntityMapper.entityToModel(technologyEntity))
        .willReturn(new Technology(1L, "Java","Java con versión JDK 17"));

    //WHEN
    var result = technologyPersistenceAdapter.saveTechnology(giveTechnology);

    //THAT
    assertEquals(expectedTechnology, result);
  }

  @DisplayName("given the name of a technology must return an optional with technology since it is already registered.")
  @Test
  void test2() {

    //GIVEN
    Technology technologyMapped = new Technology(1L, "Java", "Java con versión JDK 17");
    Optional<Technology> optionalTechnology = Optional.of(new Technology(1L, "Java", "Java con versión JDK 17"));
    Optional<TechnologyEntity> technologyEntity = Optional.of(new TechnologyEntity(1L, "Java", "Java con versión JDK 17", new ArrayList<>()));

    given(technologyRepository.findByNameIgnoreCase(this.name)).willReturn(technologyEntity);
    given(technologyEntityMapper.entityToModel(technologyEntity.get())).willReturn(technologyMapped);

    //WHEN
    var result = technologyPersistenceAdapter.verifyByName(this.name);

    //THAT
    assertEquals(optionalTechnology, result);
  }

  @DisplayName("given the name of a technology must return an empty optional since it is not registered")
  @Test
  void test3() {

    //GIVEN
    Optional<Technology> expectedTechnology = Optional.empty();
    given(technologyRepository.findByNameIgnoreCase(this.name)).willReturn(Optional.empty());

    //WHEN
    var result = technologyPersistenceAdapter.verifyByName(this.name);

    //THAT
    assertEquals(expectedTechnology, result);
  }
}