package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.IVersionServicePort;
import com.pragma.bootcamp.domain.model.Version;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.spi.IVersionPersistencePort;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;

public class VersionUseCase implements IVersionServicePort {

  private final IVersionPersistencePort versionPersistencePort;

  public VersionUseCase(IVersionPersistencePort versionPersistencePort) {
    this.versionPersistencePort = versionPersistencePort;
  }
  @Override
  public Version create(Version version) {
    return versionPersistencePort.saveVersion(version);
  }
}
