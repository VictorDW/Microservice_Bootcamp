package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.configuration.springdoc.SpringDocConstants;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("/api/technology")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class TechnologyController {

  private final ITechnologyHandler technologyHandler;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @Operation(
      summary = SpringDocConstants.OPERATION_SUMMARY_CREATE_TECHNOLOGY,
      description = SpringDocConstants.OPERATION_DESCRIPTION_CREATE_TECHNOLOGY,
      tags = {"Technology"})
  public ResponseEntity<TechnologyResponse> createTechnology(@RequestBody @Valid AddTechnologyRequest request) {

    TechnologyResponse technologyCreated = technologyHandler.createTechnology(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(technologyCreated);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  @Operation(
      summary = SpringDocConstants.OPERATION_SUMMARY_GET_TECHNOLOGY,
      description = SpringDocConstants.OPERATION_DESCRIPTION_GET_TECHNOLOGY,
      tags = {"Technology"})
  public ResponseEntity<PaginationResponse<TechnologyResponse>> getAllTechnologies(@RequestParam(required = false)
                                                                       @Min(value = 0, message = "{"+Constants.PAGE_INVALID_MESSAGE+"}")
                                                                       Integer page,
                                                                                   @RequestParam(required = false)
                                                                      @Min(value = 1, message = "{"+Constants.SIZE_INVALID_MESSAGE+"}")
                                                                      Integer size,
                                                                                   @RequestParam(required = false) String direction) {

    return ResponseEntity.ok(technologyHandler.getAllTechnologies(page, size, direction));
  }
}
