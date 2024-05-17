package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.request.ITechnologyRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.response.ITechnologyResponseMapper;
import com.pragma.bootcamp.domain.api.ITechnologyServicePort;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
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
  public PaginationResponse<TechnologyResponse> getAllTechnologies(Integer page, Integer size, String direction) {

    PaginationResponse<Technology> technologies = technologyServicePort.getAll(page, size, direction);
    return responseMapper.toPaginationResponse(technologies);
  }
}
