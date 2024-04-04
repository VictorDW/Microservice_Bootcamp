package com.pragma.bootcamp.adapters.driving.http.mapper.response;

import com.pragma.bootcamp.adapters.driving.http.dto.response.VersionResponse;
import com.pragma.bootcamp.domain.model.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface IVersionResponseMapper {
  @Mapping(target = "endDate", source = "version.endDate")
  @Mapping(target = "startDate", source = "version.startDate")
  VersionResponse modelToResponse(Version version);

  default String assignDateFormat(LocalDate date) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return date.format(formatter);
  }
}
