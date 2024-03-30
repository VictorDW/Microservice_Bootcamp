package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;

public interface IBootcampHandler {

  BootcampResponse create(AddBootcampRequest request);
}
