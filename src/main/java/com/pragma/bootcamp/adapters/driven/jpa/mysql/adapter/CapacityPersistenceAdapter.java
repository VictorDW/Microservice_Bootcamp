package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.IPaginationProvider;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.pragma.bootcamp.domain.api.usecase.CapacityUseCase.DEFAULT_ORDERING;

@RequiredArgsConstructor
public class CapacityPersistenceAdapter implements ICapacityPersistencePort, IPaginationProvider<Capacity, CapacityEntity> {

    private final ICapacityEntityMapper capacityEntityMapper;
    private final ICapacityRepository capacityRepository;

  @Override
  public Capacity saveCapacity(Capacity capacity) {

    CapacityEntity capacityEntity = capacityEntityMapper.modelToEntity(capacity);
    CapacityEntity savedCapacity = capacityRepository.save(capacityEntity);
    return capacityEntityMapper.entityToModel(savedCapacity);
  }

  @Override
  public Optional<Capacity> verifyByName(String name) {
    var existCapacityEntity = capacityRepository.findByNameIgnoreCase(name);
    return existCapacityEntity.map(capacityEntityMapper::entityToModel);
  }

  @Override
  public PaginationResponse<Capacity> getAllCapacity(PaginationData paginationData) {

    Page<CapacityEntity> pageCapacity;
    List<Capacity> modelList;
    Pageable pagination;

    if (!paginationData.property().equalsIgnoreCase(DEFAULT_ORDERING.getOrderableProperty())) {
      pagination = simplePagination(paginationData);
      pageCapacity = advancedQuery(pagination, paginationData.direction());
    } else {
      pagination = paginationWithSorting(paginationData);
      pageCapacity = capacityRepository.findAll(pagination);
    }

    modelList = capacityEntityMapper.toModelList(pageCapacity.getContent());
    return builderPaginationResponse(modelList, pageCapacity);
  }

  private Page<CapacityEntity> advancedQuery(Pageable pagination, String direction) {
    return  direction.equals(Sort.Direction.ASC.name()) ?
        capacityRepository.findAllOrderedByTechnologySizeAsc(pagination) :
        capacityRepository.findAllOrderedByTechnologySizeDesc(pagination);
  }

  @Override
  public List<Capacity> getAllCapacityWithoutPagination() {
    var capacities = capacityRepository.findAll();
    return capacityEntityMapper.toModelList(capacities);
  }
    
}
