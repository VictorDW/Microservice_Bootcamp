package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;

public interface IBootcampHandler {

  BootcampResponse createBootcamp(AddBootcampRequest request);
  PaginationResponse<BootcampResponse> getAllBootcamp(Integer page, Integer size, String direction, String orderBy);
}
