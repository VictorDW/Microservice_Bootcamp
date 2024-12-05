package com.pragma.bootcamp.domain.api;

import com.pragma.bootcamp.domain.model.Capacity;

import java.util.List;

public interface ICapacityServicePort {

  Capacity create(Capacity capacity);
  List<Capacity> getAll(Integer page, Integer size, String direction, String orderBy);
}
