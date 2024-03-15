package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.PaginationData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements ITechnologyPersistencePort {

  private final ITechnologyEntityMapper technologyEntityMapper;
  private final ITechnologyRepository technologyRepository;

  @Override
  public void saveTechnology(Technology technology) {
    technologyRepository.save(technologyEntityMapper.modelToEntity(technology));
  }

  @Override
  public Optional<Technology> verifyByName(String name) {
    return technologyRepository.findByNameIgnoreCase(name).map(technologyEntityMapper::entityToModel);
  }

  @Override
  public List<Technology> getAllTecnology(PaginationData data) {

    Sort sort = Sort.by(Sort.Direction.fromString(data.order()) , data.field());
    Pageable pagination = PageRequest.of(data.page(), data.size(), sort);

    var technologies = technologyRepository.findAll(pagination).getContent();
    return technologyEntityMapper.toModelList(technologies);
  }
}
