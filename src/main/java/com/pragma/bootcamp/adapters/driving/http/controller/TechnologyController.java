package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyServiceAdapter;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
