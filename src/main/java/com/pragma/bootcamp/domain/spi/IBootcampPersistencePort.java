package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;
import java.util.Optional;

public interface IBootcampPersistencePort {
  Bootcamp saveBootcamp(Bootcamp bootcamp);
  Optional<Bootcamp> verifyByName(String name);
  PaginationResponse<Bootcamp> getAll(PaginationData paginationData);
}
