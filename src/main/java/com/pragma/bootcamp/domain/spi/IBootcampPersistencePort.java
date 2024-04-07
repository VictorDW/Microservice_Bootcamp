package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.util.order.PaginationData;

import java.util.List;
import java.util.Optional;

public interface IBootcampPersistencePort {
  Bootcamp saveBootcamp(Bootcamp bootcamp);
  Optional<Bootcamp> verifyByName(String name);
  List<Bootcamp> getAll(PaginationData paginationData);
}
