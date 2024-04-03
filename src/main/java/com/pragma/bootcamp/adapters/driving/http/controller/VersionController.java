package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.IVersionHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.VersionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/version")
@RequiredArgsConstructor
public class VersionController {

  private final IVersionHandler versionHandler;

  @PostMapping
  public ResponseEntity<VersionResponse> createVersion(@RequestBody @Valid AddVersionRequest addVersionRequest) {

    return ResponseEntity.ok().body(versionHandler.createVersion(addVersionRequest));
  }
}
