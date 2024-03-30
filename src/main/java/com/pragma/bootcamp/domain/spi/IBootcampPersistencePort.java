package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Bootcamp;

import java.util.Optional;

public interface IBootcampPersistencePort {
  Bootcamp saveBootcamp(Bootcamp bootcamp);
  Optional<Bootcamp> verifyByName(String name);
}
