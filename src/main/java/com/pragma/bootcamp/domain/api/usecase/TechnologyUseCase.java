package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;
import com.pragma.bootcamp.domain.util.pagination.ManegePaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;

public class TechnologyUseCase implements ITechnologyServicePort {

  public static final IOrderableProperty DEFAULT_ORDERING = Technology.OrderBy.NAME;
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
  public PaginationResponse<Technology> getAll(Integer page, Integer size, String direction) {

    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, direction, DEFAULT_ORDERING.getOrderableProperty());
    return technologyPersistencePort.getAllTechnology(paginationData);
  }

  @Override
  public List<Technology> getAllWithoutPagination() {
    return technologyPersistencePort.getAllTechnologyWithoutPagination();
  }

  @Override
  public boolean isEmptyList() {
    return technologyPersistencePort.isEmptyListTechnology();
  }
}
