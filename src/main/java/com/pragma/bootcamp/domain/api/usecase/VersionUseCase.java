package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.IVersionServicePort;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Version;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.spi.IVersionPersistencePort;
import com.pragma.bootcamp.domain.util.ModelValidationUtil;
import com.pragma.bootcamp.domain.util.order.IOrderBy;
import com.pragma.bootcamp.domain.util.order.ManegePaginationData;
import com.pragma.bootcamp.domain.util.order.PaginationData;

import java.util.List;

public class VersionUseCase implements IVersionServicePort {

  public static final IOrderBy DEFAULT_ORDERING = Version.OrderBy.BOOTCAMP;
  private final IVersionPersistencePort versionPersistencePort;
  private final IMessagePort messagePort;

  public VersionUseCase(IVersionPersistencePort versionPersistencePort, IMessagePort messagePort) {
    this.versionPersistencePort = versionPersistencePort;
    this.messagePort = messagePort;
  }

  @Override
  public Version create(Version version) {
    return versionPersistencePort.saveVersion(version);
  }

  @Override
  public List<Version> getAll(Integer page, Integer size, String direction, String orderBy) {

    orderBy = ManegePaginationData.defineOrderBy(Version.OrderBy.class, DEFAULT_ORDERING, orderBy);
    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, direction, orderBy);
    List<Version> versions = versionPersistencePort.getAllVersion(paginationData);

    return ModelValidationUtil.executeValidationNotEmptyList(versions, messagePort);
  }

}
