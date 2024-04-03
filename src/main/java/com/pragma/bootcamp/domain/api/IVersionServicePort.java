package com.pragma.bootcamp.domain.api;

import com.pragma.bootcamp.domain.model.Version;

public interface IVersionServicePort {

  Version create(Version version);
}
