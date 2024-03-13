package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.configuration.util.IMessageUtil;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.exception.TechnologyAlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomainClass;

public class TechnologyUseCase implements ITechnologyServicePort {
  private final ITechnologyPersistencePort technologyPersistencePort;
  private final IMessageUtil message;


  public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort, IMessageUtil messageUtil) {
    this.technologyPersistencePort = technologyPersistencePort;
    this.message = messageUtil;
  }

  @Override
  public void create(Technology technology) {
    executeValidationExistTechnology(technology);
    technologyPersistencePort.saveTechnology(technology);
  }

  private void executeValidationExistTechnology(Technology technology) {
    technologyPersistencePort.verifyByName(technology.getName())
        .ifPresent(existTechnology-> {
          throw new TechnologyAlreadyExistException(
              message.getMessage("message.error.already.exist", DomainClass.TECHNOLOGY.getName(), existTechnology.getName())
          );
        });
  }
}
