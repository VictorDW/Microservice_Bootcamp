package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.PaginationData;

import java.util.List;
import java.util.Optional;

public interface ITechnologyPersistencePort {

  void saveTechnology(Technology technology);
  Optional<Technology> verifyByName(String name);
  List<Technology> getAllTecnology(PaginationData values);
}
