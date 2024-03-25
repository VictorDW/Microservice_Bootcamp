package com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.exception.NoEntityFoundException;
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

        CapacityEntity capacityEntity = capacityEntityMapper.modelToEntity(capacity);
        capacityEntity.setTechnologyEntities(getTechnologyEntity(capacity.getTechnologyList()));
        CapacityEntity savedCapacity = capacityRepository.save(capacityEntity);
        return capacityEntityMapper.entityToModel(savedCapacity);
    }

    private List<TechnologyEntity> getTechnologyEntity(List<Technology> technologies) {

        return technologies.stream()
            .map(technology ->
                 technologyRepository.findByNameIgnoreCase(technology.getName())
                .orElseThrow(() -> new NoEntityFoundException(
                   messagePort.getMessage(
                      Constants.NOT_FOUND_TECHNOLOGY_MESSAGE,
                      technology.getName()
                   )
                ))
            ).toList();
    }

    @Override
    public Optional<Capacity> verifyByName(String name) {
        var existCapacityEntity = capacityRepository.findByNameIgnoreCase(name);
        return existCapacityEntity.map(capacityEntityMapper::entityToModel);
    }
}
