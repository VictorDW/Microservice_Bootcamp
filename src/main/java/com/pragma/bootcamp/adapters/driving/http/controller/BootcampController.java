package com.pragma.bootcamp.adapters.driving.http.controller;


import com.pragma.bootcamp.adapters.driving.http.adapter.IBootcampHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bootcamp")
@RequiredArgsConstructor
public class BootcampController {

  private final IBootcampHandler bootcampHandler;

  @PostMapping
  public ResponseEntity<BootcampResponse> createBootcamp(@RequestBody @Valid AddBootcampRequest request) {
    var response = bootcampHandler.createBootcamp(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
