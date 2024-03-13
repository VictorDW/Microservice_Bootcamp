package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Technology;

import java.util.Optional;

public interface ITechnologyPersistencePort {

  void saveTechnology(Technology technology);

  Optional<Technology> verifyByName(String name);
}
