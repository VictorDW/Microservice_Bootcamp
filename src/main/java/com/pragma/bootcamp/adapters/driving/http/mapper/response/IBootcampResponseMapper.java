package com.pragma.bootcamp.adapters.driving.http.mapper.response;

import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityBasicResponse;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ICapacityResponseMapper.class})
public interface IBootcampResponseMapper {

	@Mapping(source = "capacityList", target = "capacities")
	BootcampResponse modelToResponse(Bootcamp bootcamp);

	@Mapping(source = "technologyList", target = "technologies")
	CapacityBasicResponse modelToResponse(Capacity capacity);

	@Mapping(source = "capacityList", target = "capacities")
	List<BootcampResponse> toResponseList(List<Bootcamp> bootcampList);
}
