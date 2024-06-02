package com.pragma.bootcamp.domain.api;

import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;

public interface ICapacityServicePort {

  Capacity create(Capacity capacity);
  PaginationResponse<Capacity> getAll(Integer page, Integer size, String direction, String orderBy);
}
