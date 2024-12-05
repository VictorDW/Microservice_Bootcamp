package com.pragma.bootcamp.adapters.driving.http.dto.response;


public record VersionResponse(
  Long id,
  String bootcampName,
  String endDate,
  String startDate,
  Integer maximumCapacity

) { }
