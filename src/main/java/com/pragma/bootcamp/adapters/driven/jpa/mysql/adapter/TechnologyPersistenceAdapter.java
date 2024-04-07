package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.IPaginationProvider;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.order.PaginationData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements ITechnologyPersistencePort, IPaginationProvider {

  private final ITechnologyEntityMapper technologyEntityMapper;
  private final ITechnologyRepository technologyRepository;

  @Override
  public Technology saveTechnology(Technology technology) {

    TechnologyEntity entity = technologyEntityMapper.modelToEntity(technology);
    TechnologyEntity savedTechnology = technologyRepository.save(entity);
    return technologyEntityMapper.entityToModel(savedTechnology);
  }

  @Override
  public Optional<Technology> verifyByName(String name) {

    var technologyEntity = technologyRepository.findByNameIgnoreCase(name);
    return technologyEntity.map(technologyEntityMapper::entityToModel);
  }

  @Override
  public List<Technology> getAllTechnology(PaginationData data) {

    Pageable pagination = paginationWithSorting(data);
    var technologyEntities = technologyRepository.findAll(pagination).getContent();

    return technologyEntityMapper.toModelList(technologyEntities);
  }
}
