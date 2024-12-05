package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;

import java.util.List;

public interface ITechnologyHandler {
  TechnologyResponse createTechnology(AddTechnologyRequest request);
  List<TechnologyResponse> getAllTechnologies(Integer page, Integer size, String direction);
}
