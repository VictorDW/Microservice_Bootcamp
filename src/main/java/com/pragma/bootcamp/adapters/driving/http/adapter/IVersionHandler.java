package com.pragma.bootcamp.adapters.driving.http.adapter;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.VersionResponse;

public interface IVersionHandler {
   VersionResponse createVersion(AddVersionRequest addVersionRequest);
}
