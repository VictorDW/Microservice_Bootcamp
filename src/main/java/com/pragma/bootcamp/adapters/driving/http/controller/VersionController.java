package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.IVersionHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.VersionResponse;
import com.pragma.bootcamp.configuration.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/version")
@RequiredArgsConstructor
@Validated
public class VersionController {

  private final IVersionHandler versionHandler;

  @PostMapping
  public ResponseEntity<VersionResponse> createVersion(@RequestBody @Valid AddVersionRequest addVersionRequest) {

    return ResponseEntity.ok().body(versionHandler.createVersion(addVersionRequest));
  }

  @GetMapping
  public ResponseEntity<List<VersionResponse>> getAllVersion(@RequestParam(required = false)
                                                               @Min(value = 1, message = "{" + Constants.PAGE_INVALID_MESSAGE + "}")
                                                               Long bootcampId,
                                                             @RequestParam(required = false)
                                                               @Min(value = 0, message = "{" + Constants.PAGE_INVALID_MESSAGE + "}")
                                                               Integer page,
                                                             @RequestParam(required = false)
                                                               @Min(value = 1, message = "{" + Constants.SIZE_INVALID_MESSAGE + "}")
                                                               Integer size,
                                                             @RequestParam(required = false) String direction,
                                                             @RequestParam(required = false) String orderBy) {

    return ResponseEntity.ok().body(versionHandler.getAllVersion(bootcampId, page, size, direction, orderBy));

  }
}
