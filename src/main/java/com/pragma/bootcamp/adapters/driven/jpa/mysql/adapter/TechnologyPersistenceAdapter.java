package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements ITechnologyPersistencePort {

  private final ITechnologyEntityMapper technologyEntityMapper;
  private final ITechnologyRepository technologyRepository;

  @Override
  public void saveTechnology(Technology technology) {
    technologyRepository.save(technologyEntityMapper.ModelToEntity(technology));
  }

  @Override
  public Optional<Technology> verifyByName(String name) {
    return technologyRepository.findByNameIgnoreCase(name).map(technologyEntityMapper::EntityToModel);
  }
}
