package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Version;
import com.pragma.bootcamp.domain.util.order.PaginationData;

import java.util.List;

public interface IVersionPersistencePort {

  Version saveVersion(Version version);

  List<Version> getAllVersion(PaginationData paginationData);


}
