package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ICapacityHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityBasicResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
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
@RequestMapping("/api/capacity")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
@Validated
public class CapacityController {

    private final ICapacityHandler capacityHandler;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @Operation(
      summary = SpringDocConstants.OPERATION_SUMMARY_CREATE_CAPACITY,
      description = SpringDocConstants.OPERATION_DESCRIPTION_CREATE_CAPACITY,
      tags = {"Capacity"}
  )
  public ResponseEntity<CapacityResponse> createCapacity(@RequestBody @Valid AddCapacityRequest request) {
    CapacityResponse capacity = capacityHandler.createCapacity(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(capacity);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  @Operation(
      summary = SpringDocConstants.OPERATION_SUMMARY_GET_CAPACITY,
      description = SpringDocConstants.OPERATION_DESCRIPTION_GET_CAPACITY,
      tags = {"Capacity"}
  )
  public ResponseEntity<PaginationResponse<CapacityResponse>> getAllCapacity(@RequestParam(required = false)
                                                               @Min(value = 0, message = "{" + Constants.PAGE_INVALID_MESSAGE + "}")
                                                               Integer page,
                                                               @RequestParam(required = false)
                                                               @Min(value = 1, message = "{" + Constants.SIZE_INVALID_MESSAGE + "}")
                                                               Integer size,
                                                               @RequestParam(required = false) String direction,
                                                               @RequestParam(required = false) String orderBy) {
    return ResponseEntity.ok(capacityHandler.getAllCapacity(page, size, direction, orderBy));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/without-pagination")
  @Operation(
      summary = SpringDocConstants.OPERATION_DESCRIPTION_GET_ALL_CAPACITY,
      description = SpringDocConstants.OPERATION_DESCRIPTION_GET_ALL_CAPACITY,
      tags = {"Capacity"}
  )
  public ResponseEntity<List<CapacityBasicResponse>> getAllCapacityWithoutPagination() {
    return ResponseEntity.ok(capacityHandler.getAllWithoutPagination());
  }
}
