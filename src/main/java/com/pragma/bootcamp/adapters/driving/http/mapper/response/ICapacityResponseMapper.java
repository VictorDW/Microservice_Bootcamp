package com.pragma.bootcamp.adapters.driving.http.mapper.response;

import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityBasicResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ITechnologyResponseMapper.class})
public interface ICapacityResponseMapper {

    @Mapping(source = "technologyList", target = "technologies")
    CapacityResponse modelToResponse(Capacity capacity);

    @Mapping(source = "technologyList", target = "technologies")
    List<CapacityResponse> toResponseList(List<Capacity> capacities);


    //Métodos usados por otro mapper
    @Mapping(source = "technologyList", target = "technologies")
    CapacityBasicResponse modelToBasicResponse(Capacity capacity);

    @Mapping(source = "technologyList", target = "technologies")
    List<CapacityBasicResponse> toBasicResponseList(List<Capacity> capacities);
}
