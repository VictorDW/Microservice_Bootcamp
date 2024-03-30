package com.pragma.bootcamp.adapters.driving.http.mapper.response;

import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITechnologyResponseMapper {

  TechnologyResponse modelToResponse(Technology technology);
  List<TechnologyResponse> toTecnologyResponseList(List<Technology> technologies);
}
