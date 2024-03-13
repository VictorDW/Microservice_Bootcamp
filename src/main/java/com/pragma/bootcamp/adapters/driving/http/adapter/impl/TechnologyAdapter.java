package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyAdapter;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TechnologyAdapter implements ITechnologyAdapter {

  private final ITechnologyServicePort technologyServicePort;
  private final ITechnologyRequestMapper requestMapper;

  @Override
  public void createTechnology(AddTechnologyRequest request) {
    technologyServicePort.create(requestMapper.createRequestToTechnology(request));
  }
}
