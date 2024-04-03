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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
  private List<TechnologyBasicResponse> technologyResponses;
  private CapacityResponse response;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(capacityController).build();

    this.technologyResponses = List.of(
        new TechnologyBasicResponse(1L, "Java"),
        new TechnologyBasicResponse(2L, "PHP"),
        new TechnologyBasicResponse(3L, "Python")
    );

    this.response = new CapacityResponse(1L, "Backend Java", "Java Backend Developer", technologyResponses);
  }

  public static Stream<Arguments> provideCapacitiesToCreateWithValidationErrors() {
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
  @DisplayName("Given an http request you should create a capacity")
  void test1() throws Exception {

    //GIVEN

    given(capacityHandler.createCapacity(any(AddCapacityRequest.class))).willReturn(response);

    String bodyRequest = "{\"name\":\"Backend Java\"," +
        "\"description\":\"Java Backend Developer\"," +
        "\"technologies\":[" +
        "{\"name\":\"Javascript\"}," +
        "{\"name\":\"PHP\"}," +
        "{\"name\":\"Python\"}]" +
        "}";

    //WHEN
    MockHttpServletRequestBuilder requestBuilder = post("/api/capacity")
        .contentType(MediaType.APPLICATION_JSON)
        .content(bodyRequest);

    //THAT
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Backend Java"))
        .andExpect(jsonPath("$.technologies").isNotEmpty());

  }

  @ParameterizedTest
  @MethodSource("provideCapacitiesToCreateWithValidationErrors")
  @DisplayName("should return a status 400 (bad request) when sending a technology with validation errors")
  void test2(String bodyRequest) throws Exception {

    //WHEN

    MockHttpServletRequestBuilder requestBuilder = post("/api/capacity")
        .contentType(MediaType.APPLICATION_JSON)
        .content(bodyRequest);

    //THEN

    mockMvc.perform(requestBuilder)
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Given an http request you should get all capacities, and return a status 200 (Ok)")
  void test3() throws Exception {

    //GIVEN
    given(capacityHandler.getAllCapacity(any(Integer.class), any(Integer.class), any(String.class), any(String.class))).willReturn(List.of(response));

    //WHEN
    MockHttpServletRequestBuilder requestBuilder = get("/api/capacity?=page=0&size=10&direction=ASC&orderBy=ca")
        .contentType(MediaType.APPLICATION_JSON);

    //THAT
    mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Backend Java"))
        .andExpect(jsonPath("$[0].technologies").isNotEmpty());
  }
}