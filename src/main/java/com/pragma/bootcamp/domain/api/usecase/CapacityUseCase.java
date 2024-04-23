package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.ICapacityServicePort;
import com.pragma.bootcamp.domain.exception.AlreadyExistException;
import com.pragma.bootcamp.domain.exception.NoDataFoundException;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;
import com.pragma.bootcamp.domain.util.pagination.ManegePaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;

import java.util.List;

public class CapacityUseCase implements ICapacityServicePort {

  public static final IOrderableProperty DEFAULT_ORDERING = Capacity.OrderBy.NAME;
  private final ICapacityPersistencePort capacityPersistencePort;
  private final IMessagePort messagePort;

  public CapacityUseCase(ICapacityPersistencePort capacityPersistencePort, IMessagePort messagePort) {
    this.capacityPersistencePort = capacityPersistencePort;
    this.messagePort = messagePort;
  }

  @Override
  public Capacity create(Capacity capacity) {
    executeValidationCapacityAlreadyExist(capacity.getName());
    return capacityPersistencePort.saveCapacity(capacity);
  }

  private void executeValidationCapacityAlreadyExist(String name) {

    var verifiedCapacity = capacityPersistencePort.verifyByName(name);

    verifiedCapacity.ifPresent(
       existCapacity -> {

           throw new AlreadyExistException(
               messagePort.getMessage(
                   DomainConstants.ALREADY_EXIST_MESSAGE,
                   DomainConstants.Class.CAPACITY.getName(),
                   existCapacity.getName()
               )
           );
       });
  }

  @Override
  public List<Capacity> getAll(Integer page, Integer size, String direction, String orderBy) {

    orderBy = ManegePaginationData.defineOrderBy(Capacity.OrderBy.class, DEFAULT_ORDERING, orderBy);
    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, direction, orderBy);
    List<Capacity> capacities = capacityPersistencePort.getAllCapacity(paginationData);

    return executeValidationNotEmptyCapacityList(capacities);
  }

  private List<Capacity> executeValidationNotEmptyCapacityList(List<Capacity> capacities) {

    if (capacities.isEmpty()) {
      throw new NoDataFoundException(messagePort.getMessage(DomainConstants.EMPTY_LIST_MESSAGE));
    }
    return capacities;
  }
}
