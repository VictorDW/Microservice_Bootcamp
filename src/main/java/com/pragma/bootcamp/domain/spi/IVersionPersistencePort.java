package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Version;

public interface IVersionPersistencePort {

  Version saveVersion(Version version);


}
