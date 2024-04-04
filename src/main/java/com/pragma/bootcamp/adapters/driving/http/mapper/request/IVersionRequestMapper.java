package com.pragma.bootcamp.adapters.driving.http.mapper.request;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.bootcamp.domain.model.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface IVersionRequestMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "endDate", source = "addVersionRequest.endDate")
  @Mapping(target = "startDate", source = "addVersionRequest.startDate")
  Version requestToModel(AddVersionRequest addVersionRequest);

  default LocalDate assignDateFormat(String date) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(date,formatter);
  }
}
