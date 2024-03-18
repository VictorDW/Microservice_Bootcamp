package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.NoDataFoundException;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.exception.TechnologyAlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.ManegePaginationData;
import com.pragma.bootcamp.domain.util.PaginationData;

import java.util.List;

public class TechnologyUseCase implements ITechnologyServicePort {
  private final ITechnologyPersistencePort technologyPersistencePort;
  private final IMessagePort messagePort;


  public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort, IMessagePort messageUtil) {
    this.technologyPersistencePort = technologyPersistencePort;
    this.messagePort = messageUtil;
  }

  @Override
  public void create(Technology technology) {

    executeValidationTechnologyAlreadyExist(technology);
    technologyPersistencePort.saveTechnology(technology);
  }

  private void executeValidationTechnologyAlreadyExist(Technology technology) {

   var verifyTechnology = technologyPersistencePort.verifyByName(technology.getName());

    verifyTechnology.ifPresent(
        existingTechnology-> {

          throw new TechnologyAlreadyExistException(
              messagePort.getMessage(
                  DomainConstants.ALREADY_EXIST_MESSAGE,
                  DomainConstants.Class.TECHNOLOGY,
                  existingTechnology.getName())
          );
        });
  }

  @Override
  public List<Technology> getAll(Integer page, Integer size, String order) {

    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, order);
    List<Technology> allTechnology = technologyPersistencePort.getAllTecnology(paginationData);
    return executeValidateNotEmptyTechnologyList(allTechnology);
  }

  private List<Technology> executeValidateNotEmptyTechnologyList(List<Technology> technologies) {

    if (technologies.isEmpty()) {
      throw new NoDataFoundException(messagePort.getMessage(DomainConstants.EMPTY_LIST_MESSAGE));
    }
    return technologies;
  }
}
