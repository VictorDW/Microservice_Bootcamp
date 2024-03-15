package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.NoDataFoundException;
import com.pragma.bootcamp.domain.util.IMessageUtil;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.exception.TechnologyAlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomainClass;
import com.pragma.bootcamp.domain.util.ManegePaginationData;
import com.pragma.bootcamp.domain.util.PaginationData;

import java.util.List;

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
              message.getMessage("error.already.exist.message", DomainClass.TECHNOLOGY.getName(), existTechnology.getName())
          );
        });
  }

  @Override
  public List<Technology> getAll(Integer page, Integer size, String order) {
    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, order);
    return executeValidationTechnologiesNotFound(technologyPersistencePort.getAllTecnology(paginationData));
  }

  private List<Technology> executeValidationTechnologiesNotFound(List<Technology> technologies) {
    if (technologies.isEmpty()) {
      throw new NoDataFoundException(message.getMessage("empty.list.message"));
    }
    return technologies;
  }
}
