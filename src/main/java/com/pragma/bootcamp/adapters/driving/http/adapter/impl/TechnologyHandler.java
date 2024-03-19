package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyServiceAdapter;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TechnologyHandler implements ITechnologyServiceAdapter {

  private final ITechnologyServicePort technologyServicePort;
  private final ITechnologyRequestMapper requestMapper;
  private final ITechnologyResponseMapper responseMapper;

  @Override
  public void createTechnology(AddTechnologyRequest request) {
    technologyServicePort.create(requestMapper.createRequestToTechnology(request));
  }

  @Override
  public List<TechnologyResponse> getAllTechnologies(Integer page, Integer size, String order) {
    return responseMapper.toTecnologyResponseList(technologyServicePort.getAll(page, size, order));
  }
}
