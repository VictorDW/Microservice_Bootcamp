package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.IBootcampServicePort;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.spi.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;
import com.pragma.bootcamp.domain.util.pagination.ManegePaginationData;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

public class BootcampUseCase implements IBootcampServicePort {

  public static final IOrderableProperty DEFAULT_ORDERING = Bootcamp.OrderBy.NAME;
  private final IBootcampPersistencePort bootcampPersistencePort;
  private final IMessagePort messagePort;

  public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, IMessagePort messagePort) {
    this.bootcampPersistencePort = bootcampPersistencePort;
    this.messagePort = messagePort;
  }

  @Override
  public Bootcamp create(Bootcamp bootcamp) {

   executeNameVerification(bootcamp.getName());
   return bootcampPersistencePort.saveBootcamp(bootcamp);
  }

  private void executeNameVerification(String name) {

    var verifyBootcamp = bootcampPersistencePort.verifyByName(name);

    ModelValidationUtil.validationModelAlreadyExist(
        verifyBootcamp,
        DomainConstants.Class.BOOTCAMP.getName(),
        messagePort);
  }

  @Override
  public PaginationResponse<Bootcamp> getAll(Integer page, Integer size, String direction, String orderBy) {

    orderBy = ManegePaginationData.defineOrderBy(Bootcamp.OrderBy.class, DEFAULT_ORDERING, orderBy);
    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, direction, orderBy);

    return bootcampPersistencePort.getAll(paginationData);
  }

}
