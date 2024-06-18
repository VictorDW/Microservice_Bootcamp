package com.pragma.bootcamp.domain.api.usecase;

import com.pragma.bootcamp.domain.api.IVersionServicePort;
import com.pragma.bootcamp.domain.model.Version;
import com.pragma.bootcamp.domain.spi.IVersionPersistencePort;
import com.pragma.bootcamp.domain.util.orderby.VersionOrderBy;
import com.pragma.bootcamp.domain.util.pagination.IOrderableProperty;
import com.pragma.bootcamp.domain.util.pagination.ManegePaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;

import java.util.List;

public class VersionUseCase implements IVersionServicePort {

  public static final IOrderableProperty DEFAULT_ORDERING = VersionOrderBy.BOOTCAMP;
  private final IVersionPersistencePort versionPersistencePort;

  public VersionUseCase(IVersionPersistencePort versionPersistencePort) {
    this.versionPersistencePort = versionPersistencePort;
  }

  @Override
  public Version create(Version version) {
    return versionPersistencePort.saveVersion(version);
  }

  @Override
  public List<Version> getAll(Long bootcampId, Integer page, Integer size, String direction, String orderBy) {

    orderBy = ManegePaginationData.defineOrderBy(VersionOrderBy.class, DEFAULT_ORDERING, orderBy);
    PaginationData paginationData = ManegePaginationData.definePaginationData(page, size, direction, orderBy);

    return versionPersistencePort.getAllVersion(bootcampId, paginationData);
  }

}
