package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;
import java.util.Optional;

public interface ICapacityPersistencePort {

  Capacity saveCapacity(Capacity capacity);
  Optional<Capacity> verifyByName(String name);
  PaginationResponse<Capacity> getAllCapacity(PaginationData paginationData);
  List<Capacity> getAllCapacityWithoutPagination();
}
