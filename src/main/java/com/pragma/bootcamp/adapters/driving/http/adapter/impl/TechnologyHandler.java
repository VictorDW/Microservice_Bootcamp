package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.model.Technology;
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
  public TechnologyResponse createTechnology(AddTechnologyRequest request) {

    var toTechnology = requestMapper.requestToModel(request);
    var technologyCreated = technologyServicePort.create(toTechnology);

    return responseMapper.modelToResponse(technologyCreated);
  }

  @Override
  public List<TechnologyResponse> getAllTechnologies(Integer page, Integer size, String order) {

    List<Technology> technologies = technologyServicePort.getAll(page, size, order);
    return responseMapper.toTecnologyResponseList(technologies);
  }
}
