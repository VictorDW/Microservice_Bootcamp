package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.IVersionHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.VersionResponse;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.configuration.springdoc.SpringDocConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/version")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
@Validated
public class VersionController {

  private final IVersionHandler versionHandler;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @Operation(
      summary = SpringDocConstants.OPERATION_SUMMARY_CREATE_VERSION,
      description = SpringDocConstants.OPERATION_DESCRIPTION_CREATE_VERSION,
      tags = {"Version"}
  )
  public ResponseEntity<VersionResponse> createVersion(@RequestBody @Valid AddVersionRequest addVersionRequest) {

    return ResponseEntity.ok().body(versionHandler.createVersion(addVersionRequest));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  @Operation(
      summary = SpringDocConstants.OPERATION_SUMMARY_GET_VERSION,
      description = SpringDocConstants.OPERATION_DESCRIPTION_GET_VERSION,
      tags = {"Version"}
  )
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
