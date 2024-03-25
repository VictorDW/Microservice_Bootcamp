package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.ICapacityHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.ICapacityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.ICapacityResponseMapper;
import com.pragma.bootcamp.domain.api.ICapacityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CapacityHandler implements ICapacityHandler {

    private final ICapacityServicePort capacityServicePort;
    private final ICapacityRequestMapper capacityRequestMapper;
    private final ICapacityResponseMapper capacityResponseMapper;
    @Override
    public CapacityResponse createCapacity(AddCapacityRequest request) {
        var capacity = capacityRequestMapper.requestToModel(request);
        var savedCapacity = capacityServicePort.create(capacity);
        return capacityResponseMapper.modelToResponse(savedCapacity);
    }
}
