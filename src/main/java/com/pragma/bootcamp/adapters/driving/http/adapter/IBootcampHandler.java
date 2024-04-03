package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;

import java.util.List;

public interface IBootcampHandler {

  BootcampResponse createBootcamp(AddBootcampRequest request);
  List<BootcampResponse> getAllBootcamp(Integer page, Integer size, String direction, String orderBy);
}
