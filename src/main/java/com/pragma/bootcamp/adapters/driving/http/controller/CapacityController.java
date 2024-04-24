package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ICapacityHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.configuration.Constants;
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
@RequiredArgsConstructor
@Validated
public class CapacityController {

    private final ICapacityHandler capacityHandler;


  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<CapacityResponse> createCapacity(@RequestBody @Valid AddCapacityRequest request) {
    CapacityResponse capacity = capacityHandler.createCapacity(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(capacity);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<CapacityResponse>> getAllCapacity(@RequestParam(required = false)
                                                               @Min(value = 0, message = "{" + Constants.PAGE_INVALID_MESSAGE + "}")
                                                               Integer page,
                                                               @RequestParam(required = false)
                                                               @Min(value = 1, message = "{" + Constants.SIZE_INVALID_MESSAGE + "}")
                                                               Integer size,
                                                               @RequestParam(required = false) String direction,
                                                               @RequestParam(required = false) String orderBy) {
    return ResponseEntity.ok(capacityHandler.getAllCapacity(page, size, direction, orderBy));
  }
}
