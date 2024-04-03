package com.pragma.bootcamp.adapters.driving.http.mapper.request;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.bootcamp.domain.model.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IVersionRequestMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "endDate", source = "addVersionRequest.endDate")
  @Mapping(target = "startDate", source = "addVersionRequest.startDate")
  Version requestToModel(AddVersionRequest addVersionRequest);
}
