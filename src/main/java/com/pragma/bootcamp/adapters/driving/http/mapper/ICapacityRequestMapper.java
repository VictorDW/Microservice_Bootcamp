package com.pragma.bootcamp.adapters.driving.http.mapper;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICapacityRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "technologyBasicList", target = "technologyList")
    Capacity requestToModel(AddCapacityRequest addCapacityRequest);
}
