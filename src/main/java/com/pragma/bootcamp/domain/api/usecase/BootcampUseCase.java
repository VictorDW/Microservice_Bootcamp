package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.IBootcampServicePort;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.spi.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.ManegePaginationData;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;
import com.pragma.bootcamp.domain.util.PaginationData;

import java.util.List;

public class BootcampUseCase implements IBootcampServicePort {

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
  public List<Bootcamp> getAll(Integer page, Integer size, String direction, String orderBy) {

    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, direction, orderBy);
    List<Bootcamp> bootcamps = bootcampPersistencePort.getAll(paginationData);
    return ModelValidationUtil.executeValidationNotEmptyBootcampList(bootcamps,messagePort);
  }

}
