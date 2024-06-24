package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.exception.NoEntityFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.IPaginationProvider;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.spi.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import com.pragma.bootcamp.domain.util.pagination.PaginationData;
import com.pragma.bootcamp.domain.util.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.pragma.bootcamp.domain.api.usecase.BootcampUseCase.DEFAULT_ORDERING;

@RequiredArgsConstructor
public class BootcampPersistenceAdapter implements IBootcampPersistencePort, IPaginationProvider<Bootcamp, BootcampEntity> {

	private final IBootcampEntityMapper bootcampEntityMapper;
	private final IBootcampRepository bootcampRepository;

	@Override
	public Bootcamp saveBootcamp(Bootcamp bootcamp) {

		BootcampEntity bootcampEntity = bootcampEntityMapper.modelToEntity(bootcamp);
		BootcampEntity savedBootcamp = bootcampRepository.save(bootcampEntity);
		return bootcampEntityMapper.entityToModel(savedBootcamp);
	}

	@Override
	public Optional<Bootcamp> verifyByName(String name) {
		var bootcampEntity = bootcampRepository.findByNameIgnoreCase(name);
		return bootcampEntity.map(bootcampEntityMapper::entityToModel);
	}

	@Override
	public PaginationResponse<Bootcamp> getAll(PaginationData paginationData) {

		Page<BootcampEntity> pageBootcamp;
		List<Bootcamp> modelList;
		Pageable pagination;

		if (!paginationData.property().equalsIgnoreCase(DEFAULT_ORDERING.getOrderableProperty())) {
			pagination = simplePagination(paginationData);
			pageBootcamp = queryAdvance(pagination, paginationData.direction());
		} else {
			pagination = paginationWithSorting(paginationData);
			pageBootcamp = bootcampRepository.findAll(pagination);
		}

		modelList = bootcampEntityMapper.toModelList(pageBootcamp.getContent());

		return builderPaginationResponse(modelList, pageBootcamp);
	}

	private Page<BootcampEntity> queryAdvance(Pageable pagination, String direction) {
		return direction.equals(Sort.Direction.ASC.name()) ?
				bootcampRepository.findAllOrderedByBootcampSizeAsc(pagination) :
				bootcampRepository.findAllOrderedByBootcampSizeDesc(pagination);
	}
}
