package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoEntityFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.order.ManegePaginationData;
import com.pragma.bootcamp.domain.util.order.PaginationData;
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
	@Mock
	private ICapacityRepository capacityRepository;
	@Mock
	private IMessagePort messagePort;
	@Captor
	private ArgumentCaptor<Specification<BootcampEntity>> specificationCaptor;
	private List<Capacity> capacities;
	private Bootcamp givenBootcamp;
	private Bootcamp response;
	private List<BootcampEntity> allEntity;
	private List<Bootcamp> allModel;

	@BeforeEach
	void setUp() {

		this.capacities = List.of(
				new Capacity(null, "Test capacity", null)
		);
		this.givenBootcamp = new Bootcamp(1L, "Test bootcamp", "Test");
		this.givenBootcamp.setCapacityList(capacities);
		this.response = givenBootcamp;
		this.allEntity = List.of(new BootcampEntity());
		this.allModel = List.of(response);
	}

	@Test
	@DisplayName("Should throw an exception when trying to create a bootcamp with a non-existing capacity, and the message key must match the contents of the message.properties")
	void test1() {

		//GIVEN
		String nameCapacity = capacities.get(0).getName();
		BootcampEntity bootcampEntity = new BootcampEntity();
		String keyMessage = "error.not.found.capacity.message";

		given(bootcampEntityMapper.modelToEntity(givenBootcamp)).willReturn(bootcampEntity);
		given(capacityRepository.findByNameIgnoreCase(nameCapacity)).willReturn(Optional.empty());
		given(messagePort.getMessage(Constants.NOT_FOUND_CAPACITY_MESSAGE, nameCapacity)).willReturn(Constants.NOT_FOUND_CAPACITY_MESSAGE);

		//WHEN - THAT
		assertAll(
				() -> assertThrows(NoEntityFoundException.class, ()-> bootcampPersistenceAdapter.saveBootcamp(givenBootcamp)),
				() -> {
					try{
						bootcampPersistenceAdapter.saveBootcamp(givenBootcamp);
					}catch (NoEntityFoundException e) {
						assertEquals(keyMessage, e.getMessage());
					}
				}
		);
	}
	
	@Test
	@DisplayName("Must allow for the creation of a bootcamp")
	void test2() {

		//GIVEN
		BootcampEntity bootcampEntity = new BootcampEntity();
		CapacityEntity  capacityEntity = new CapacityEntity();

		given(bootcampEntityMapper.modelToEntity(givenBootcamp)).willReturn(bootcampEntity);
		given(capacityRepository.findByNameIgnoreCase(any(String.class))).willReturn(Optional.of(capacityEntity));
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
	@DisplayName("Must return a list containing the bootcamps from pagination with simple ordering")
	void test5() {

		//GIVEN
		PaginationData paginationData = ManegePaginationData.definePaginationData(1,10, "asc", "name");
		Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
		Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size(), sort);

		given(bootcampRepository.findAll(pagination)).willReturn(new PageImpl<>(allEntity));
		given(bootcampEntityMapper.toModelList(allEntity)).willReturn(allModel);

		//WHEN
		var result = bootcampPersistenceAdapter.getAll(paginationData);

		//THAT
		assertThat(result).isNotEmpty();
	}

	@Test
	@DisplayName("Must return a list containing the bootcamps from the pagination with advanced query")
	void test6() {

		//GIVEN
		PaginationData paginationData = ManegePaginationData.definePaginationData(0,10, "asc", "capacities");
		Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size());

		given(bootcampRepository.findAll(specificationCaptor.capture(), eq(pagination))).willReturn(new PageImpl<>(allEntity));
		given(bootcampEntityMapper.toModelList(allEntity)).willReturn(allModel);

		//WHEN
		var result = bootcampPersistenceAdapter.getAll(paginationData);

		//THAT
		assertThat(result).isNotEmpty();
	}

	@Test
	@DisplayName("Must return an empty list of bootcamps, since no results were found in the database")
	void test7() {

		//GIVEN
		PaginationData paginationData = ManegePaginationData.definePaginationData(1,10, "asc", "capacities");
		Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size());

		given(bootcampRepository.findAll(specificationCaptor.capture(), eq(pagination))).willReturn(new PageImpl<>(new ArrayList<>()));


		//WHEN
		var result = bootcampPersistenceAdapter.getAll(paginationData);

		//THAT
		assertThat(result).isEmpty();
	}
}