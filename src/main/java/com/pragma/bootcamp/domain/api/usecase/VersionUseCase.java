package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.IVersionServicePort;
import com.pragma.bootcamp.domain.model.Version;

public class VersionUseCase implements IVersionServicePort {

  @Override
  public Version create(Version version) {
    return new Version(version.getId(), version.getBootcampName(), version.getStartDate(), version.getEndDate(), version.getMaximumCapacity());
  }
}
