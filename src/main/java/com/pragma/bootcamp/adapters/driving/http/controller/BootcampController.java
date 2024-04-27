package com.pragma.bootcamp.adapters.driving.http.controller;


import com.pragma.bootcamp.adapters.driving.http.adapter.IBootcampHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.configuration.springdoc.SpringDocConstants;
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
@RequestMapping("/api/bootcamp")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
@Validated
public class BootcampController {

  private final IBootcampHandler bootcampHandler;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @Operation(
      summary = SpringDocConstants.OPERATION_SUMMARY_CREATE_BOOTCAMP,
      description = SpringDocConstants.OPERATION_DESCRIPTION_CREATE_BOOTCAMP,
      tags = {"Bootcamp"}
  )
  public ResponseEntity<BootcampResponse> createBootcamp(@RequestBody @Valid AddBootcampRequest request) {
    var response = bootcampHandler.createBootcamp(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  @Operation(
      summary = SpringDocConstants.OPERATION_SUMMARY_GET_BOOTCAMP,
      description = SpringDocConstants.OPERATION_DESCRIPTION_GET_BOOTCAMP,
      tags = {"Bootcamp"}
  )
  public ResponseEntity<List<BootcampResponse>> getAllBootcamp(@RequestParam(required = false)
                                                                 @Min(value = 0, message = "{"+ Constants.PAGE_INVALID_MESSAGE+"}")
                                                                 Integer page,
                                                               @RequestParam(required = false)
                                                                 @Min(value = 1, message = "{"+Constants.SIZE_INVALID_MESSAGE+"}")
                                                                 Integer size,
                                                               @RequestParam(required = false) String direction,
                                                               @RequestParam(required = false) String orderBy) {

    return ResponseEntity.ok(bootcampHandler.getAllBootcamp(page, size, direction, orderBy));
  }
}
