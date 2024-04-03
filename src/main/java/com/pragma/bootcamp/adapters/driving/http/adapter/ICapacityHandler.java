package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;

import java.util.List;

public interface ICapacityHandler {
  CapacityResponse createCapacity(AddCapacityRequest request);
  List<CapacityResponse> getAllCapacity(Integer page, Integer size, String direction, String orderBy);
}
