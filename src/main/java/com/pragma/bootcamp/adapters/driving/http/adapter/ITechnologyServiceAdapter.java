package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;

public interface ITechnologyServiceAdapter {
  void createTechnology(AddTechnologyRequest request);
}
