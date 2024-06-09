package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.adapter.IBootcampHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.request.IBootcampRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.response.IBootcampResponseMapper;
import com.pragma.bootcamp.domain.api.IBootcampServicePort;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BootcampHandler implements IBootcampHandler {

	private final IBootcampServicePort bootcampServicePort;
	private final IBootcampRequestMapper bootcampRequestMapper;
	private final IBootcampResponseMapper bootcampResponseMapper;

	@Override
	public BootcampResponse createBootcamp(AddBootcampRequest request) {

		var bootcamp = bootcampRequestMapper.requestToModel(request);
		var response = bootcampServicePort.create(bootcamp);
		return bootcampResponseMapper.modelToResponse(response);
	}

	@Override
	public PaginationResponse<BootcampResponse> getAllBootcamp(Integer page, Integer size, String direction, String orderBy) {

		PaginationResponse<Bootcamp> bootcamps = bootcampServicePort.getAll(page,size,direction,orderBy);
		return bootcampResponseMapper.toPaginationResponse(bootcamps);
	}
}
