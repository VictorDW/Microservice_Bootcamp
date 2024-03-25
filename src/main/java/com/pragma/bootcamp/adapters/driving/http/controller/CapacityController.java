package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ICapacityHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/capacity")
@RequiredArgsConstructor
public class CapacityController {

    private final ICapacityHandler capacityHandler;


    @PostMapping
    public ResponseEntity<CapacityResponse> createCapacity(@RequestBody @Valid AddCapacityRequest request) {
        CapacityResponse capacity = capacityHandler.createCapacity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(capacity);
    }
}
