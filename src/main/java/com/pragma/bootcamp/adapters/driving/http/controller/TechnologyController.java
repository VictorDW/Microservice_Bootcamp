package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.bootcamp.configuration.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("/api/technology")
@RequiredArgsConstructor
public class TechnologyController {

  private final ITechnologyHandler technologyHandler;

  @PostMapping
  public ResponseEntity<Void> createTechnology(@RequestBody @Valid AddTechnologyRequest request) {
    technologyHandler.createTechnology(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<List<TechnologyResponse>> getAllTechnologies(@RequestParam(required = false)
                                                                       @Min(value = 0, message = "{"+Constants.PAGE_INVALID_MESSAGE+"}")
                                                                       Integer page,
                                                                    @RequestParam(required = false)
                                                                      @Min(value = 1, message = "{"+Constants.SIZE_INVALID_MESSAGE+"}")
                                                                      Integer size,
                                                                    @RequestParam(required = false) String order) {

    return ResponseEntity.ok(technologyHandler.getAllTechnologies(page, size, order));
  }
}
