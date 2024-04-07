package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.util.order.PaginationData;

import java.util.List;
import java.util.Optional;

public interface ICapacityPersistencePort {

  Capacity saveCapacity(Capacity capacity);
  Optional<Capacity> verifyByName(String name);
  List<Capacity> getAllCapacity(PaginationData paginationData);
}
