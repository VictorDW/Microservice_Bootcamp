package com.pragma.bootcamp.domain.api;

import com.pragma.bootcamp.domain.model.Bootcamp;

import java.util.List;

public interface IBootcampServicePort {
  Bootcamp create(Bootcamp bootcamp);
  List<Bootcamp> getAll(Integer page, Integer size, String direction, String orderBy);
}
