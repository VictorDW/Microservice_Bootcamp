package com.pragma.bootcamp.adapters.driving.http.mapper;

import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = ITechnologyResponseMapper.class)
public interface ICapacityResponseMapper {

    @Mapping(source = "technologyList", target = "technologies")
    CapacityResponse modelToResponse(Capacity capacity);
    @Mapping(source = "technologyList", target = "technologies")
    List<CapacityResponse> toResponseList(List<Capacity> capacities);
}
