package com.pragma.bootcamp.domain.api;


import com.pragma.bootcamp.domain.model.Technology;

import java.util.List;

public interface ITechnologyServicePort {

  void create(Technology technology);

  List<Technology> getAll(Integer page, Integer size, String order);
}
