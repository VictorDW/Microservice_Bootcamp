package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.VersionResponse;

import java.util.List;

public interface IVersionHandler {

   VersionResponse createVersion(AddVersionRequest addVersionRequest);
   List<VersionResponse> getAllVersion(Long bootcampId, Integer page, Integer size, String direction, String orderBy);
}
