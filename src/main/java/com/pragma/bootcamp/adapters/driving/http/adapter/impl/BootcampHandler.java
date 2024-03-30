package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.IBootcampHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.request.IBootcampRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.response.IBootcampResponseMapper;
import com.pragma.bootcamp.domain.api.IBootcampServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BootcampHandler implements IBootcampHandler {

	private final IBootcampServicePort bootcampServicePort;
	private final IBootcampRequestMapper bootcampRequestMapper;
	private final IBootcampResponseMapper bootcampResponseMapper;

	@Override
	public BootcampResponse create(AddBootcampRequest request) {

		var bootcamp = bootcampRequestMapper.requestToModel(request);
		var response = bootcampServicePort.create(bootcamp);
		return bootcampResponseMapper.modelToResponse(response);
	}
}
