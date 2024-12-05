package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.exception.NoEntityFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.pagination.ManegePaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BootcampPersistenceAdapterTest {

	@InjectMocks
	private BootcampPersistenceAdapter bootcampPersistenceAdapter;

	@Mock
	private IBootcampEntityMapper bootcampEntityMapper;
	@Mock
	private IBootcampRepository bootcampRepository;

	private Bootcamp givenBootcamp;
	private Bootcamp response;
	private BootcampEntity bootcampEntity;

	@BeforeEach
	void setUp() {

		Capacity capacity = new Capacity(1L, "Test capacity", "Test");
		List<Capacity> capacities = List.of(capacity);

		this.givenBootcamp = new Bootcamp(1L, "Test bootcamp", "Test");
		this.givenBootcamp.setCapacityList(capacities);
		this.response = givenBootcamp;
		this.bootcampEntity = new BootcampEntity();
	}
	
	@Test
	@DisplayName("Must allow for the creation of a bootcamp")
	void test2() {

		//GIVEN
		BootcampEntity bootcampEntity = new BootcampEntity();

		given(bootcampEntityMapper.modelToEntity(givenBootcamp)).willReturn(bootcampEntity);
		given(bootcampRepository.save(bootcampEntity)).willReturn(bootcampEntity);
		given(bootcampEntityMapper.entityToModel(bootcampEntity)).willReturn(response);

		//WHEN
		var result = bootcampPersistenceAdapter.saveBootcamp(givenBootcamp);

		//THAT
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("Given the name of a bootcamp, it must return an Optional with the bootcamp found")
	void test3() {

		//GIVEN
		String nameBootcamp = "Test";
		BootcampEntity bootcampEntity = new BootcampEntity();

		given(bootcampRepository.findByNameIgnoreCase(nameBootcamp)).willReturn(Optional.of(bootcampEntity));
		given(bootcampEntityMapper.entityToModel(bootcampEntity)).willReturn(new Bootcamp(1L,"Test", "Test"));

		//WHEN
		var result = bootcampPersistenceAdapter.verifyByName(nameBootcamp);

		//THAT
		assertThat(result).isNotEmpty();
	}

	@Test
	@DisplayName("Given the name of a bootcamp, it must return an empty optional if the bootcamp does not exist")
	void test4() {

		//GIVEN
		String nameBootcamp = "Test";
		given(bootcampRepository.findByNameIgnoreCase(nameBootcamp)).willReturn(Optional.empty());

		//WHEN
		var result = bootcampPersistenceAdapter.verifyByName(nameBootcamp);

		//THAT
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Must return a non-null PaginationResponse object with the bootcamps and the data for pagination with simple ordering")
	void test5() {

		//GIVEN
		PaginationData paginationData = ManegePaginationData.definePaginationData(1,10, "asc", "name");
		Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
		Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size(), sort);

		given(bootcampRepository.findAll(pagination)).willReturn(new PageImpl<>(List.of(bootcampEntity)));
		given(bootcampEntityMapper.toModelList(List.of(bootcampEntity))).willReturn(List.of(response));

		//WHEN
		var result = bootcampPersistenceAdapter.getAll(paginationData);

		//THAT
		assertThat(result.getContent()).isNotEmpty();
		assertThat(result.isFirst()).isTrue();
		assertThat(result.isLast()).isTrue();
		assertThat(result.getPageNumber()).isZero();
		assertThat(result.getPageSize()).isEqualTo(1);
		assertThat(result.getTotalElements()).isPositive();
		assertThat(result.getTotalPages()).isPositive();
	}

	@Test
	@DisplayName("Must return a non-null PaginationResponse object with the bootcamps and the data for pagination with advance query")
	void test6() {

		//GIVEN
		PaginationData paginationData = ManegePaginationData.definePaginationData(0,10, "asc", "capacities");
		Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size());

		given(bootcampRepository.findAllOrderedByBootcampSizeAsc(pagination)).willReturn(new PageImpl<>(List.of(bootcampEntity)));
		given(bootcampEntityMapper.toModelList(List.of(bootcampEntity))).willReturn(List.of(response));

		//WHEN
		var result = bootcampPersistenceAdapter.getAll(paginationData);

		//THAT
		assertThat(result.getContent()).isNotEmpty();
		assertThat(result.isFirst()).isTrue();
		assertThat(result.isLast()).isTrue();
		assertThat(result.getPageNumber()).isZero();
		assertThat(result.getPageSize()).isEqualTo(1);
		assertThat(result.getTotalElements()).isPositive();
		assertThat(result.getTotalPages()).isPositive();
	}

	@Test
	@DisplayName("Must return an empty list of bootcamps, since no results were found in the database")
	void test7() {

		//GIVEN
		PaginationData paginationData = ManegePaginationData.definePaginationData(1,10, "asc", "capacities");
		Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size());

		given(bootcampRepository.findAllOrderedByBootcampSizeAsc(pagination)).willReturn(new PageImpl<>(new ArrayList<>()));


		//WHEN
		var result = bootcampPersistenceAdapter.getAll(paginationData);

		//THAT
		assertThat(result.getContent()).isEmpty();
	}
}