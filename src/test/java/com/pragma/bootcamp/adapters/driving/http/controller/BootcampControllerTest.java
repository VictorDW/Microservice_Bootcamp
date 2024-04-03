package com.pragma.bootcamp.adapters.driving.http.controller;

import com.pragma.bootcamp.adapters.driving.http.adapter.IBootcampHandler;
import com.pragma.bootcamp.adapters.driving.http.dto.request.AddBootcampRequest;
import com.pragma.bootcamp.adapters.driving.http.dto.response.BootcampResponse;
import com.pragma.bootcamp.adapters.driving.http.dto.response.CapacityBasicResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BootcampControllerTest {

	@InjectMocks
	private BootcampController bootcampController;

	@Mock
	private IBootcampHandler bootcampHandler;
	private MockMvc mockMvc;

	private List<TechnologyBasicResponse> TechnologyBasicResponse;
	private List<CapacityBasicResponse> capacityBasicResponses;
	private BootcampResponse bootcampResponse;


	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(bootcampController).build();

		this.TechnologyBasicResponse = List.of(
				new TechnologyBasicResponse(1L, "Java"),
				new TechnologyBasicResponse(2L, "PHP"),
				new TechnologyBasicResponse(3L, "Python")
		);

		 capacityBasicResponses = List.of(
				new CapacityBasicResponse(1L, "Test capacity 1", TechnologyBasicResponse)
		);

		bootcampResponse = new BootcampResponse(1L, "Test Bootcamp","Test", capacityBasicResponses);
	}

	public static Stream<Arguments> provideBootcampToCreateWithValidationErrors() {
		return Stream.of(
				Arguments.of(
						"{\"name\":\"\"," +
								"\"description\":\"Java con versión JDK 17\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}," +
								"{\"name\":\"PHP\"}," +
								"{\"name\":\"Python\"}]}"
				),
				Arguments.of(
						"{\"name\":," +
								"\"description\":\"Java con versión JDK 17\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"P\"," +
								"\"description\":\"Java con versión JDK 17\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"Ppppppppppppppppppppppppppppppppppppppppppppppppppppppp\"," +
								"\"description\":\"Java con versión JDK 17\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"Python45-\"," +
								"\"description\":\"Java con versión JDK 17\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"Java\"," +
								"\"description\":," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"Java\"," +
								"\"description\":\"\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"Java\"," +
								"\"description\":\"Java\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"Java\"," +
								"\"description\":\"jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"Java\"," +
								"\"description\":\"Java con versión JDK 17@\"," +
								"\"capacities\":[" +
								"{\"name\":\"Javascript\"}]}"
				),
				Arguments.of(
						"{\"name\":\"Java\"," +
								"\"description\":\"Java con versión JDK 17\"," +
								"\"capacities\":}"
				),
				Arguments.of(
						"{\"name\":\"Java\"," +
								"\"description\":\"Java con versión JDK 17\"," +
								"\"capacities\":[]}"
				),
				Arguments.of(
						"{\"name\":\"Java\"," +
								"\"description\":\"Java con versión JDK 17\"," +
								"\"capacities\":[" +
								"{\"name\":}," +
								"{\"name\":\"\"}," +
								"{\"name\":\"Python@\"}]}"
				)
		);
	}
	@Test
	@DisplayName("Given an http request you should create a bootcamp, and return a status 201 (Created)")
	void test1() throws Exception {

		//GIVEN
		given(bootcampHandler.createBootcamp(any(AddBootcampRequest.class))).willReturn(bootcampResponse);

		String bodyRequest = "{\"name\":\"Test Bootcamp\"," +
												"\"description\":\"Test Bootcamp\"," +
												"\"capacities\":[" +
												"{\"name\":\"Test capacity 1\"}]}";

		//WHEN

		MockHttpServletRequestBuilder requestBuilder = post("/api/bootcamp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyRequest);

		//THAT

		mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("Test Bootcamp"))
				.andExpect(jsonPath("$.capacities").isNotEmpty());
	}

	@ParameterizedTest()
	@MethodSource("provideBootcampToCreateWithValidationErrors")
	@DisplayName("should return a status 400 (bad request) when sending a technology with validation errors")
	void test2(String bodyRequest) throws Exception {

		//WHEN

		MockHttpServletRequestBuilder requestBuilder = post("/api/bootcamp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bodyRequest);

		//THEN

		mockMvc.perform(requestBuilder)
				.andExpect(status().isBadRequest());
	}

}