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
import com.pragma.bootcamp.domain.spi.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BootcampPersistenceAdapter implements IBootcampPersistencePort, IPaginationProvider, IQuerySpecificationProvider<BootcampEntity, CapacityEntity> {

	private final IBootcampEntityMapper bootcampEntityMapper;
	private final ICapacityRepository capacityRepository;
	private final IBootcampRepository bootcampRepository;
	private final IMessagePort messagePort;

	@Override
	public Bootcamp saveBootcamp(Bootcamp bootcamp) {

		BootcampEntity bootcampEntity = bootcampEntityMapper.modelToEntity(bootcamp);
		bootcampEntity.setCapacityEntities(getCapacityEntity(bootcamp.getCapacityList()));
		BootcampEntity savedBootcamp = bootcampRepository.save(bootcampEntity);
		return bootcampEntityMapper.entityToModel(savedBootcamp);
	}

	private List<CapacityEntity> getCapacityEntity(List<Capacity> capacityList) {

		return capacityList.stream()
				.map(capacity ->
						capacityRepository.findByNameIgnoreCase(capacity.getName())
						.orElseThrow(() ->
								new NoEntityFoundException(messagePort.getMessage(
												Constants.NOT_FOUND_CAPACITY_MESSAGE,
												capacity.getName()))
						)
				)
				.toList();
	}

	@Override
	public Optional<Bootcamp> verifyByName(String name) {
		var bootcampEntity = bootcampRepository.findByNameIgnoreCase(name);
		return bootcampEntity.map(bootcampEntityMapper::entityToModel);
	}
}
