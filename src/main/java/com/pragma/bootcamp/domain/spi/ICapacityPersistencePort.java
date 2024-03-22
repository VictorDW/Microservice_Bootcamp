package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Capacity;

import java.util.Optional;

public interface ICapacityPersistencePort {

  Capacity saveCapacity(Capacity capacity);
  Optional<Capacity> verifyByName(String name);
}
