package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;
import java.util.Optional;

public interface ITechnologyPersistencePort {

  Technology saveTechnology(Technology technology);
  Optional<Technology> verifyByName(String name);
  PaginationResponse<Technology> getAllTechnology(PaginationData values);
  List<Technology> getAllTechnologyWithoutPagination();
}
