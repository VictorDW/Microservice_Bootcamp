package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.exception.NoDataFoundException;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.order.IOrderBy;
import com.pragma.bootcamp.domain.util.order.ManegePaginationData;
import com.pragma.bootcamp.domain.util.order.PaginationData;

import java.util.List;

public class TechnologyUseCase implements ITechnologyServicePort {

  public static final IOrderBy DEFAULT_ORDERING = Technology.OrderBy.NAME;
  private final ITechnologyPersistencePort technologyPersistencePort;
  private final IMessagePort messagePort;


  public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort, IMessagePort messageUtil) {
    this.technologyPersistencePort = technologyPersistencePort;
    this.messagePort = messageUtil;
  }

  @Override
  public Technology create(Technology technology) {

    executeValidationTechnologyAlreadyExist(technology);
    return technologyPersistencePort.saveTechnology(technology);
  }

  private void executeValidationTechnologyAlreadyExist(Technology technology) {

   var verifyTechnology = technologyPersistencePort.verifyByName(technology.getName());

    verifyTechnology.ifPresent(
        existingTechnology-> {

          throw new AlreadyExistException(
              messagePort.getMessage(
                  DomainConstants.ALREADY_EXIST_MESSAGE,
                  DomainConstants.Class.TECHNOLOGY.getName(),
                  existingTechnology.getName())
          );
        });
  }

  @Override
  public List<Technology> getAll(Integer page, Integer size, String direction) {

    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, direction, DEFAULT_ORDERING.getOrderBy());
    List<Technology> technologies = technologyPersistencePort.getAllTechnology(paginationData);

    return executeValidateNotEmptyTechnologyList(technologies);
  }

  private List<Technology> executeValidateNotEmptyTechnologyList(List<Technology> technologies) {

    if (technologies.isEmpty()) {
      throw new NoDataFoundException(messagePort.getMessage(DomainConstants.EMPTY_LIST_MESSAGE));
    }
    return technologies;
  }
}
