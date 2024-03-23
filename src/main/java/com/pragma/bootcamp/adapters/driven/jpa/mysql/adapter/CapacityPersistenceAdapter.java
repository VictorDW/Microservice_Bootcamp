package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NotFoundException;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import com.pragma.bootcamp.domain.spi.ICapacityPersistencePort;
import com.pragma.bootcamp.domain.spi.IMessagePort;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CapacityPersistenceAdapter implements ICapacityPersistencePort {

    private final ICapacityEntityMapper capacityEntityMapper;
    private final ITechnologyRepository technologyRepository;
    private final ICapacityRepository capacityRepository;
    private final IMessagePort messagePort;

    public CapacityPersistenceAdapter(ICapacityEntityMapper capacityEntityMapper, ITechnologyRepository technologyRepository, ICapacityRepository capacityRepository, IMessagePort messagePort) {
        this.capacityEntityMapper = capacityEntityMapper;
        this.technologyRepository = technologyRepository;
        this.capacityRepository = capacityRepository;
        this.messagePort = messagePort;
    }

    @Override
    public Capacity saveCapacity(Capacity capacity) {

        var capacityEntity = capacityEntityMapper.modelToEntity(capacity);
        capacityEntity.setTechnologyEntities(getTechnologyEntity(capacity.getTechnologyList()));
        var savedCapacity = capacityRepository.save(capacityEntity);
        return capacityEntityMapper.entityToModel(savedCapacity);
    }

    private Set<TechnologyEntity> getTechnologyEntity(List<Technology> technologies) {

        return technologies.stream()
            .map(technology -> technologyRepository.findByNameIgnoreCase(technology.getName())
                .orElseThrow(() -> new NotFoundException(
                   messagePort.getMessage(
                      Constants.NOT_FOUND_TECHNOLOGY_MESSAGE,
                      technology.getName()
                   )
                ))
            ).collect(Collectors.toSet());
    }

    @Override
    public Optional<Capacity> verifyByName(String name) {
        return Optional.empty();
    }
}
