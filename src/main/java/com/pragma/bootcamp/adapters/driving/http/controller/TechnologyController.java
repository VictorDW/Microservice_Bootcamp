package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyServiceAdapter;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/techology")
@RequiredArgsConstructor
public class TechnologyController {

  private final ITechnologyServiceAdapter technologyHandler;

  @PostMapping
  public ResponseEntity<Void> createTechnology(@RequestBody @Valid AddTechnologyRequest request) {
    technologyHandler.createTechnology(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<List<TechnologyResponse>> getAllTechnologies(@RequestParam(required = false) Integer page,
                                                                    @RequestParam(required = false) Integer size,
                                                                    @RequestParam(required = false) String order) {

    return ResponseEntity.ok(technologyHandler.getAllTechnologies(page, size, order));
  }
}
