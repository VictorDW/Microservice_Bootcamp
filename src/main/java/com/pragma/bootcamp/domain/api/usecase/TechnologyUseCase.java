package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.exception.TechnologyAlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;

public class TechnologyUseCase implements ITechnologyServicePort {
  private final ITechnologyPersistencePort technologyPersistencePort;

  public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort) {
    this.technologyPersistencePort = technologyPersistencePort;
  }

  @Override
  public void create(Technology technology) {
    technologyPersistencePort.verifyByName(technology.getName())
        .ifPresent(isTechnology-> {throw new TechnologyAlreadyExistException("La tecnologia ya esta creada");});

    technologyPersistencePort.saveTechnology(technology);
  }
}
