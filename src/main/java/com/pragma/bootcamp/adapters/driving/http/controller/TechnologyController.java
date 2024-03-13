package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyAdapter;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
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

  private final ITechnologyAdapter adapter;

  @PostMapping
  public ResponseEntity<Void> createTechnology(@RequestBody AddTechnologyRequest request) {
    adapter.createTechnology(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
