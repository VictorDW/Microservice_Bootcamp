package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.ICapacityHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.request.ICapacityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.response.ICapacityResponseMapper;
import com.pragma.bootcamp.domain.api.ICapacityServicePort;
import com.pragma.bootcamp.domain.model.Capacity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<CapacityResponse> getAllCapacity(Integer page, Integer size, String direction, String orderBy) {

        List<Capacity> capacities = capacityServicePort.getAll(page, size, direction, orderBy);
        return capacityResponseMapper.toResponseList(capacities);
    }


}
