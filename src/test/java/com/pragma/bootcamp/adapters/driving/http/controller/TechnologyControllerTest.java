package com.pragma.bootcamp.adapters.driving.http.controller;


import com.pragma.bootcamp.adapters.driving.http.adapter.ITechnologyHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TechnologyControllerTest {
    @Mock
    private ITechnologyHandler technologyHandler;
    @InjectMocks
    private TechnologyController restController;
    private MockMvc mockMvc;
    private TechnologyResponse response;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
        response = new TechnologyResponse(1L,"Java","Java con versión JDK 17");
    }

    @DisplayName("Given an http request you should create a technology")
    @Test
    void test1() throws Exception {

        given(technologyHandler.createTechnology(any(AddTechnologyRequest.class))).willReturn(response);

        String bodyRequest = "{\"name\":\"Python\",\"description\":\"Java con versión JDK 17\"}";

        MockHttpServletRequestBuilder requestBuilder = post("/api/technology")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyRequest);

        //GIVEN

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));

        //WHEN - THEN
    }

    public static Stream<Arguments> provideTechnologiesToCreateWithValidationErrors() {
        return Stream.of(
            Arguments.of(
                    "{\"name\":\"\",\"description\":\"Java con versión JDK 17\"}"
            ),
            Arguments.of(
                "{\"name\":,\"description\":\"Java con versión JDK 17\"}"
            ),
            Arguments.of(
                "{\"name\":\"P\",\"description\":\"Java con versión JDK 17\"}"
            ),
            Arguments.of(
                "{\"name\":\"Ppppppppppppppppppppppppppppppppppppppppppppppppppppppp\",\"description\":\"Java con versión JDK 17\"}"
            ),
            Arguments.of(
                "{\"name\":\"Python45-\",\"description\":\"Java con versión JDK 17\"}"
            ),
            Arguments.of(
                "{\"name\":\"Java\",\"description\":}"
            ),
            Arguments.of(
                "{\"name\":\"Java\",\"description\":\"\"}"
            ),
            Arguments.of(
                "{\"name\":\"Java\",\"description\":\"Java\"}"
            ),
            Arguments.of(
                "{\"name\":\"Java\",\"description\":\"jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj\"}"
            ),
            Arguments.of(
                "{\"name\":\"Java\",\"description\":\"Java con versión JDK 17@\"}"
            )
        );
    }


    @ParameterizedTest
    @MethodSource("provideTechnologiesToCreateWithValidationErrors")
    @DisplayName("should return a status 400 (bad request) when sending a technology with validation errors.")
    void test2(String bodyRequest) throws Exception {

        //WHEN

        MockHttpServletRequestBuilder requestBuilder = post("/api/technology")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyRequest);

        //THEN

        mockMvc.perform(requestBuilder)
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given an http request you should get all technologies")
    void test3() throws Exception {
        //GIVEN
        given(technologyHandler.getAllTechnologies(any(Integer.class), any(Integer.class), any(String.class))).willReturn(List.of(response));

        //WHEN
        MockHttpServletRequestBuilder requestBuilder = get("/api/technology?page=0&size=10&direction=asc")
            .contentType(MediaType.APPLICATION_JSON);

        //THEN
        mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk());
    }

}