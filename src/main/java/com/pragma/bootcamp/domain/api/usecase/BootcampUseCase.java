package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.IBootcampServicePort;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.spi.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.DomainConstants;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;

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
}
