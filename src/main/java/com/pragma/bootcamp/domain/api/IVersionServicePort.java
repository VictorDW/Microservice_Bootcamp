package com.pragma.bootcamp.domain.api;

import com.pragma.bootcamp.domain.model.Version;

import java.util.List;

public interface IVersionServicePort {

  Version create(Version version);

  List<Version> getAll(Integer page, Integer size, String direction, String orderBy);
}
