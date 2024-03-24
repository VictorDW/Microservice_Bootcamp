package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyHandler implements ITechnologyHandler {

  private final ITechnologyServicePort technologyServicePort;
  private final ITechnologyRequestMapper requestMapper;
  private final ITechnologyResponseMapper responseMapper;

  @Override
  public void createTechnology(AddTechnologyRequest request) {
    technologyServicePort.create(requestMapper.requestToModel(request));
  }

  @Override
  public List<TechnologyResponse> getAllTechnologies(Integer page, Integer size, String order) {
    return responseMapper.toTecnologyResponseList(technologyServicePort.getAll(page, size, order));
  }
}
