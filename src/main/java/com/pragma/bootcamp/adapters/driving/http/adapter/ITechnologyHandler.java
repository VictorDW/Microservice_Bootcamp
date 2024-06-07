package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyBasicResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;

public interface ITechnologyHandler {
  TechnologyResponse createTechnology(AddTechnologyRequest request);
  PaginationResponse<TechnologyResponse> getAllTechnologies(Integer page, Integer size, String direction);
  List<TechnologyBasicResponse> getAllWithoutPagination();
  boolean isEmptyList();
}
