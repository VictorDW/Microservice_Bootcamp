package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.IVersionHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.VersionResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.request.IVersionRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.response.IVersionResponseMapper;
import com.pragma.bootcamp.domain.api.IVersionServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VersionHandler implements IVersionHandler {

  private final IVersionServicePort versionServicePort;
  private final IVersionRequestMapper versionRequestMapper;
  private final IVersionResponseMapper versionResponseMapper;

  @Override
  public VersionResponse createVersion(AddVersionRequest addVersionRequest) {
    var version = versionRequestMapper.requestToModel(addVersionRequest);
    var response = versionServicePort.create(version);
    return versionResponseMapper.modelToResponse(response);
  }
}
