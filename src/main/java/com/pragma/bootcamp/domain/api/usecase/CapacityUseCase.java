package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.ICapacityServicePort;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;

public class CapacityUseCase implements ICapacityServicePort {

  private final ICapacityPersistencePort capacityPersistencePort;

  public CapacityUseCase(ICapacityPersistencePort capacityPersistencePort) {
    this.capacityPersistencePort = capacityPersistencePort;
  }

  @Override
  public Capacity create(Capacity capacity) {
    return capacityPersistencePort.saveCapacity(capacity);
  }
}
