package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CapacityPersistenceAdapterTest {

  @InjectMocks
  private CapacityPersistenceAdapter capacityPersistenceAdapter;
  @Mock
  private  ICapacityEntityMapper capacityEntityMapper;
  @Mock
  private  ICapacityRepository capacityRepository;

  private Capacity givenCapacity;
  private Capacity response;
  private List<CapacityEntity> allEntity;
  private List<Capacity> allModel;

  @BeforeEach
  void setUp() {
    List<Technology> technologies = List.of(
        new Technology(null, "Java", null),
        new Technology(null, "Python", null),
        new Technology(null, "Javascript", null)
    );
    Capacity capacity = new Capacity(1L, "Backend Java", "Test");
    capacity.setTechnologyList(technologies);

    this.allEntity = List.of(new CapacityEntity(1L, "Backend Java", "Test", new ArrayList<>(), new ArrayList<>()));
    this.allModel = List.of(capacity);
    this.givenCapacity = new Capacity(1L, "Backend Java", "Java Backend Developer");
    this.response = this.givenCapacity;
  }

  @Test
  @DisplayName("Must allow for the creation of a capacity")
  void test2() {

    //GIVEN
    CapacityEntity capacityEntity = new CapacityEntity();

    given(capacityEntityMapper.modelToEntity(givenCapacity)).willReturn(capacityEntity);
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
  @DisplayName("Must return a non-null PaginationResponse object with the pagination capabilities and data with simple ordering")
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
    assertThat(result.getContent()).isNotEmpty();
    assertThat(result.isFirst()).isTrue();
    assertThat(result.isLast()).isTrue();
    assertThat(result.getPageNumber()).isZero();
    assertThat(result.getPageSize()).isEqualTo(1);
    assertThat(result.getTotalElements()).isPositive();
    assertThat(result.getTotalPages()).isPositive();
  }

  @Test
  @DisplayName("Must return a non-null PaginationResponse object with advanced query paging capabilities and data")
  void test6() {

    //GIVEN
    PaginationData paginationData = new PaginationData(0, 10, "DESC", "technologies");
    Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size());

    given(capacityRepository.findAllOrderedByTechnologySizeDesc(pagination)).willReturn(new PageImpl<>(allEntity));
    given(capacityEntityMapper.toModelList(allEntity)).willReturn(allModel);

    //WHEN

    var result = capacityPersistenceAdapter.getAllCapacity(paginationData);

    //THAT
    assertThat(result.getContent()).isNotEmpty();
    assertThat(result.isFirst()).isTrue();
    assertThat(result.isLast()).isTrue();
    assertThat(result.getPageNumber()).isZero();
    assertThat(result.getPageSize()).isEqualTo(1);
    assertThat(result.getTotalElements()).isPositive();
    assertThat(result.getTotalPages()).isPositive();
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
    assertThat(result.getContent()).isEmpty();
  }
}