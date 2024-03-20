package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TechnologyPersistenceAdapterTest {

  @Mock
  private ITechnologyEntityMapper technologyEntityMapper;
  @Mock
  private ITechnologyRepository technologyRepository;
  @InjectMocks
  private TechnologyPersistenceAdapter technologyPersistenceAdapter;


  @DisplayName("Given a technology you should insert it in the DB")
  @Test
  void test1() {

    //GIVEN
    Technology giveTechnology = new Technology(1L, "Java", "Java con versión JDK 17");
    Technology expectedTechnology = new Technology(1L, "Java", "Java con versión JDK 17");
    TechnologyEntity technologyEntity = new TechnologyEntity(1L, "Java", "Java con versión JDK 17");

    given(technologyEntityMapper.modelToEntity(giveTechnology)).willReturn(technologyEntity);
    given(technologyRepository.save(technologyEntity)).willReturn(technologyEntity);
    given(technologyEntityMapper.entityToModel(technologyEntity)).willReturn(giveTechnology);

    //WHEN
    var result = technologyPersistenceAdapter.saveTechnology(giveTechnology);

    //THAT
    assertEquals(expectedTechnology, result);
  }
}