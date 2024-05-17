package com.pragma.bootcamp.domain.api;


import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;

public interface ITechnologyServicePort {

  Technology create(Technology technology);

  PaginationResponse<Technology> getAll(Integer page, Integer size, String order);
}
