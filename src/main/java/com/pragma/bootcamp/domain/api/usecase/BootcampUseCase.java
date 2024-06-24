package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.IBootcampServicePort;
import com.pragma.bootcamp.domain.exception.NoEntityFoundException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.orderby.BootcampOrderBy;
import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;
import com.pragma.bootcamp.domain.util.pagination.ManegePaginationData;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;
import java.util.Optional;

public class BootcampUseCase implements IBootcampServicePort {

  public static final IOrderableProperty DEFAULT_ORDERING = BootcampOrderBy.NAME;
  private final IBootcampPersistencePort bootcampPersistencePort;
  private final ICapacityPersistencePort capacityPersistencePort;
  private final IMessagePort messagePort;

  public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, ICapacityPersistencePort capacityPersistencePort, IMessagePort messagePort) {
    this.bootcampPersistencePort = bootcampPersistencePort;
    this.capacityPersistencePort = capacityPersistencePort;
    this.messagePort = messagePort;
  }

  @Override
  public Bootcamp create(Bootcamp bootcamp) {

   executeNameVerification(bootcamp.getName());
   addVerifiedCapacities(bootcamp);
   return bootcampPersistencePort.saveBootcamp(bootcamp);
  }

  private void executeNameVerification(String name) {

    var verifyBootcamp = bootcampPersistencePort.verifyByName(name);
    ModelValidationUtil.validationModelAlreadyExist(verifyBootcamp, DomainConstants.Class.BOOTCAMP.getName(), messagePort);
  }

  private void addVerifiedCapacities(Bootcamp bootcamp) {

    List<Capacity> capacities = verifyExistenceCapacities(bootcamp.getCapacityList());
    bootcamp.setCapacityList(capacities);
  }

  private List<Capacity> verifyExistenceCapacities(List<Capacity> capacities) {
    return capacities.stream()
        .map(capacity -> verifyCapacityByName(capacity.getName()))
        .toList();
  }

  private Capacity verifyCapacityByName(String name) {

    Optional<Capacity> capacity = capacityPersistencePort.verifyByName(name);

    return capacity.orElseThrow(() ->
        new NoEntityFoundException(messagePort.getMessage(
            DomainConstants.NOT_FOUND_CAPACITY_MESSAGE,
            name)));
  }


  @Override
  public PaginationResponse<Bootcamp> getAll(Integer page, Integer size, String direction, String orderBy) {

    orderBy = ManegePaginationData.defineOrderBy(BootcampOrderBy.class, DEFAULT_ORDERING, orderBy);
    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, direction, orderBy);

    return bootcampPersistencePort.getAll(paginationData);
  }

}
