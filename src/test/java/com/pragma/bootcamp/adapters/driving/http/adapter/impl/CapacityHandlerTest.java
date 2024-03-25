package com.pragma.bootcamp.adapters.driving.http.adapter.impl;

import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.request.TechnologyBasicRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyBasicResponse;
import com.pragma.bootcamp.adapters.driving.http.mapper.ICapacityRequestMapper;
import com.pragma.bootcamp.adapters.driving.http.mapper.ICapacityResponseMapper;
import com.pragma.bootcamp.domain.api.ICapacityServicePort;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CapacityHandlerTest {

    @InjectMocks
    private CapacityHandler capacityHandler;
    @Mock
    private  ICapacityServicePort capacityServicePort;
    @Mock
    private  ICapacityRequestMapper capacityRequestMapper;
    @Mock
    private  ICapacityResponseMapper capacityResponseMapper;

    @Test
    @DisplayName("Given a request it must allow to create a capability and return it ")
    void test1() {

        //GIVEN
        List<TechnologyBasicRequest> technologyBasic = List.of(
                new TechnologyBasicRequest("Java"),
                new TechnologyBasicRequest("Python"),
                new TechnologyBasicRequest("JavaScript"));

        List<Technology> technologies = List.of(
                new Technology(null, "Java", null),
                new Technology(null, "Python", null),
                new Technology(null, "Javascript", null)
        );

        List<TechnologyBasicResponse> TechnologyResponses = List.of(
                new TechnologyBasicResponse(1L, "Java"),
                new TechnologyBasicResponse(2L, "Python"),
                new TechnologyBasicResponse(3L, "Javascript")
        );

        var request = new AddCapacityRequest("Backend Java", "Java Backend Developer",technologyBasic);
        var capacity = new Capacity(1L, "Backend Java", "Java Backend Developer", technologies);
        var response = new CapacityResponse(1L, "Backend Java", "Java Backend Developer",TechnologyResponses);

        given(capacityRequestMapper.requestToModel(request)).willReturn(capacity);
        given(capacityServicePort.create(capacity)).willReturn(capacity);
        given(capacityResponseMapper.modelToResponse(capacity)).willReturn(response);

        //WHEN

        var result = capacityHandler.createCapacity(request);

        //THAT
        assertThat(result).isNotNull();
    }

}