package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;

public interface ICapacityHandler {

    CapacityResponse createCapacity(AddCapacityRequest request);
}
