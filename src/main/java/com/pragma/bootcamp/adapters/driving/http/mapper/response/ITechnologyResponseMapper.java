package com.pragma.bootcamp.adapters.driving.http.mapper.response;

import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyBasicResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring" , builder = @Builder(disableBuilder = true))
public interface ITechnologyResponseMapper {

  TechnologyResponse modelToResponse(Technology technology);
  List<TechnologyResponse> toTecnologyResponseList(List<Technology> technologies);

  default PaginationResponse<TechnologyResponse> toPaginationResponse(PaginationResponse<Technology> paginationTechnologies) {

    if ( paginationTechnologies == null ) {
      return null;
    }

    return new PaginationResponse.Builder<TechnologyResponse>()
      .content(toTecnologyResponseList(paginationTechnologies.getContent()))
      .isEmpty( paginationTechnologies.isEmpty())
      .isFirst( paginationTechnologies.isFirst())
      .isLast( paginationTechnologies.isLast())
      .pageNumber( paginationTechnologies.getPageNumber())
      .pageSize( paginationTechnologies.getPageSize())
      .totalElements( paginationTechnologies.getTotalElements())
      .totalPages( paginationTechnologies.getTotalPages())
      .build();
  }

  //Métodos usados por otros mapper
  TechnologyBasicResponse modelToBasicResponse(Technology technology);
  List<TechnologyBasicResponse> toBasicResponseList(List<Technology> technologies);
}
