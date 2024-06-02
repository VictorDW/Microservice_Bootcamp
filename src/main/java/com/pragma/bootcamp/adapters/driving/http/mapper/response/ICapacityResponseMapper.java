package com.pragma.bootcamp.adapters.driving.http.mapper.response;

import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityBasicResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ITechnologyResponseMapper.class})
public interface ICapacityResponseMapper {

    @Mapping(source = "technologyList", target = "technologies")
    CapacityResponse modelToResponse(Capacity capacity);

    @Mapping(source = "technologyList", target = "technologies")
    List<CapacityResponse> toResponseList(List<Capacity> capacities);

    default PaginationResponse<CapacityResponse> toPaginationResponse(PaginationResponse<Capacity> paginationCapacity) {

        if ( paginationCapacity == null ) {
            return null;
        }

        return new PaginationResponse.Builder<CapacityResponse>()
            .content(toResponseList(paginationCapacity.getContent()))
            .isEmpty(paginationCapacity.isEmpty())
            .isFirst(paginationCapacity.isFirst())
            .isLast(paginationCapacity.isLast())
            .pageNumber(paginationCapacity.getPageNumber())
            .pageSize(paginationCapacity.getPageSize())
            .totalElements(paginationCapacity.getTotalElements())
            .totalPages(paginationCapacity.getTotalPages())
            .build();
    }

    //Métodos usados por otro mapper
    @Mapping(source = "technologyList", target = "technologies")
    CapacityBasicResponse modelToBasicResponse(Capacity capacity);

    @Mapping(source = "technologyList", target = "technologies")
    List<CapacityBasicResponse> toBasicResponseList(List<Capacity> capacities);
}
