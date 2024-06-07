package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

import java.util.List;

public interface ICapacityHandler {
  CapacityResponse createCapacity(AddCapacityRequest request);
  PaginationResponse<CapacityResponse> getAllCapacity(Integer page, Integer size, String direction, String orderBy);
}
