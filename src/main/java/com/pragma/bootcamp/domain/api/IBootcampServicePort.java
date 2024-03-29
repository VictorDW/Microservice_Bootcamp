package com.pragma.bootcamp.domain.api;

import com.pragma.bootcamp.domain.model.Bootcamp;

public interface IBootcampServicePort {

  Bootcamp create(Bootcamp bootcamp);
}
