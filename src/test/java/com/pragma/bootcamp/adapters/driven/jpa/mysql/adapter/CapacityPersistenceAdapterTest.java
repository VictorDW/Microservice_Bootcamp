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
import com.pragma.bootcamp.domain.util.PaginationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
  @Captor
  private ArgumentCaptor<Specification<CapacityEntity>> specificationCaptor;
  private List<Technology> technologies;
  private Capacity givenCapacity;
  private Capacity response;
  private List<CapacityEntity> allEntity;
  private List<Capacity> allModel;

  @BeforeEach
  void setUp() {
    this.technologies = List.of(
            new Technology(null, "Java", null),
            new Technology(null, "Python", null),
            new Technology(null, "Javascript", null)
    );
    this.allEntity = List.of( new CapacityEntity(1L,"Backend Java", "Test", new ArrayList<>(), new ArrayList<>()));
    this.allModel = List.of( new Capacity(1L,"Backend Java", "Test"));
    this.givenCapacity = new Capacity(1L, "Backend Java", "Java Backend Developer");
    this.givenCapacity.setTechnologyList(technologies);
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

  @Test
  @DisplayName("Must return a list containing the capacities from pagination with simple ordering")
  void test5() {

    //GIVEN
    PaginationData paginationData = new PaginationData(0, 10, "DESC", "name");

    Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
    Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size(), sort);

    given(capacityRepository.findAll(pagination)).willReturn(new PageImpl<>(allEntity));
    given(capacityEntityMapper.toModelList(allEntity)).willReturn(allModel);

    //WHEN

    var result = capacityPersistenceAdapter.getAllCapacity(paginationData);

    //THAT
    assertThat(result).isNotEmpty();
  }

  @Test
  @DisplayName("Must return a list containing the capacities from the pagination with advanced query")
  void test6() {

    //GIVEN
    PaginationData paginationData = new PaginationData(0, 10, "DESC", "technologies");
    Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size());

    given(capacityRepository.findAll(specificationCaptor.capture(),eq(pagination))).willReturn(new PageImpl<>(allEntity));
    given(capacityEntityMapper.toModelList(allEntity)).willReturn(allModel);

    //WHEN

    var result = capacityPersistenceAdapter.getAllCapacity(paginationData);

    //THAT
    assertThat(result).isNotEmpty();
  }

  @Test
  @DisplayName("Must return an empty list of capacity, since no results were found in the database")
  void test7() {

    PaginationData paginationData = new PaginationData(0, 10, "DESC", "name");
    Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
    Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size(), sort);

    given(capacityRepository.findAll(pagination)).willReturn(new PageImpl<>(new ArrayList<>()));

    //WHEN

    var result = capacityPersistenceAdapter.getAllCapacity(paginationData);

    //THAT
    assertThat(result).isEmpty();
  }
}