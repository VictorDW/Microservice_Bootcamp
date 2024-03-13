package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;

public interface ITechnologyAdapter {
  void createTechnology(AddTechnologyRequest request);
}
