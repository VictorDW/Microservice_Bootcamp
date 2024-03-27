package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.ICapacityHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddCapacityRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.TechnologyBasicResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CapacityControllerTest {

    @InjectMocks
    private CapacityController capacityController;
    @Mock
    private ICapacityHandler capacityHandler;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(capacityController).build();
    }

    public static Stream<Arguments> provideTechnologiesToCreateWithValidationErrors() {
        return Stream.of(
                Arguments.of(
                        "{\"name\":\"\"," +
                                "\"description\":\"Java con versión JDK 17\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":," +
                                "\"description\":\"Java con versión JDK 17\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"P\"," +
                                "\"description\":\"Java con versión JDK 17\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"Ppppppppppppppppppppppppppppppppppppppppppppppppppppppp\"," +
                                "\"description\":\"Java con versión JDK 17\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"Python45-\"," +
                                "\"description\":\"Java con versión JDK 17\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"Java\"," +
                                "\"description\":," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"Java\"," +
                                "\"description\":\"\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"Java\"," +
                                "\"description\":\"Java\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"Java\"," +
                                "\"description\":\"jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"Java\"," +
                                "\"description\":\"Java con versión JDK 17@\"," +
                                "\"technologies\":[" +
                                "{\"name\":\"Javascript\"}," +
                                "{\"name\":\"PHP\"}," +
                                "{\"name\":\"Python\"}]}"
                ),
                Arguments.of(
                        "{\"name\":\"Java\"," +
                                "\"description\":\"Java con versión JDK 17\"," +
                                "\"technologies\":}"
                ),
                Arguments.of(
                        "{\"name\":\"Java\"," +
                                "\"description\":\"Java con versión JDK 17\"," +
                                "\"technologies\":[]}"
                ),
                Arguments.of(
                        "{\"name\":\"Java\"," +
                                "\"description\":\"Java con versión JDK 17\"," +
                                "\"technologies\":[" +
                                "{\"name\":}," +
                                "{\"name\":\"\"}," +
                                "{\"name\":\"Python@\"}]}"
                )
        );
    }


    @Test
    void test1() throws Exception {

        List<TechnologyBasicResponse> TechnologyResponses = List.of(
                new TechnologyBasicResponse(1L, "Java"),
                new TechnologyBasicResponse(2L, "PHP"),
                new TechnologyBasicResponse(3L, "Python")
        );

        CapacityResponse response = new CapacityResponse(1L, "Backend Java", "Java Backend Developer",TechnologyResponses);

        given(capacityHandler.createCapacity(any(AddCapacityRequest.class))).willReturn(response);

        String bodyRequest = "{\"name\":\"Backend Java\"," +
                                "\"description\":\"Java Backend Developer\"," +
                                "\"technologies\":[" +
                                    "{\"name\":\"Javascript\"}," +
                                    "{\"name\":\"PHP\"}," +
                                    "{\"name\":\"Python\"}]" +
                                "}";

        MockHttpServletRequestBuilder requestBuilder = post("/api/capacity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyRequest);


        //GIVEN

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Backend Java"))
                .andExpect(jsonPath("$.technologies").isNotEmpty());

    }

    @ParameterizedTest
    @MethodSource("provideTechnologiesToCreateWithValidationErrors")
    @DisplayName("should return a status 400 (bad request) when sending a technology with validation errors")
    void test2(String bodyRequest) throws Exception {


        MockHttpServletRequestBuilder requestBuilder = post("/api/capacity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyRequest);

        //GIVEN

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        //WHEN - THEN
    }
}