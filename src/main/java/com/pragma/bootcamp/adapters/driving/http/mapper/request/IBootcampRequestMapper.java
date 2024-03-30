package com.pragma.bootcamp.adapters.driving.http.mapper.request;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.domain.model.Bootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBootcampRequestMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "capacities", target = "capacityList")
	Bootcamp requestToModel(AddBootcampRequest addBootcampRequest);

}
