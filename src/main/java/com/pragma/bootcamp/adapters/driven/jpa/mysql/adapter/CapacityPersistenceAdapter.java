package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoEntityFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.IPaginationProvider;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.IQuerySpecificationProvider;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.pragma.bootcamp.domain.api.usecase.CapacityUseCase.DEFAULT_ORDERING;

@RequiredArgsConstructor
public class CapacityPersistenceAdapter implements ICapacityPersistencePort, IPaginationProvider, IQuerySpecificationProvider<CapacityEntity, TechnologyEntity> {

    private final ICapacityEntityMapper capacityEntityMapper;
    private final ITechnologyRepository technologyRepository;
    private final ICapacityRepository capacityRepository;
    private final IMessagePort messagePort;

  @Override
  public Capacity saveCapacity(Capacity capacity) {

    CapacityEntity capacityEntity = capacityEntityMapper.modelToEntity(capacity);
    capacityEntity.setTechnologyEntities(getTechnologyEntity(capacity.getTechnologyList()));
    CapacityEntity savedCapacity = capacityRepository.save(capacityEntity);
    return capacityEntityMapper.entityToModel(savedCapacity);
  }

  private List<TechnologyEntity> getTechnologyEntity(List<Technology> technologies) {

    return technologies.stream()
        .map(technology ->
            technologyRepository.findByNameIgnoreCase(technology.getName())
            .orElseThrow(() ->
                new NoEntityFoundException(messagePort.getMessage(
                        Constants.NOT_FOUND_TECHNOLOGY_MESSAGE,
                        technology.getName()))
            )
        )
        .toList();
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
    return new PaginationResponse.Builder<Capacity>()
        .content(modelList)
        .isEmpty(pageCapacity.isEmpty())
        .isFirst(pageCapacity.isFirst())
        .isLast(pageCapacity.isLast())
        .pageNumber(pageCapacity.getNumber())
        .pageSize(pageCapacity.getSize())
        .totalElements(pageCapacity.getTotalElements())
        .totalPages(pageCapacity.getTotalPages())
        .build();
  }

  private Page<CapacityEntity> advancedQuery(Pageable pagination, String direction) {
    Specification<CapacityEntity> specification = queryWithSpecification(direction, CapacityEntity.FIELD_CONTAINING_RELATIONSHIP);
    return capacityRepository.findAll(specification, pagination);
  }
    
}
