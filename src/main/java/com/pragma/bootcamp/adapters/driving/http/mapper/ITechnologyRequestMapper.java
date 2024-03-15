package com.pragma.bootcamp.adapters.driving.http.mapper;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITechnologyRequestMapper {
  @Mapping(target = "id", ignore = true)
  Technology createRequestToTechnology(AddTechnologyRequest request);

  List<TechnologyResponse> toTecnologyResponseList(List<Technology> technologies);
}
