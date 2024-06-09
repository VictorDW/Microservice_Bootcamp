package com.pragma.bootcamp.domain.api;

import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

public interface IBootcampServicePort {
  Bootcamp create(Bootcamp bootcamp);
  PaginationResponse<Bootcamp> getAll(Integer page, Integer size, String direction, String orderBy);
}
