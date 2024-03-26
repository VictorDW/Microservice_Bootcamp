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
import com.pragma.bootcamp.domain.util.PaginationData;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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

    @Override
   public List<Capacity> getAllCapacity(PaginationData paginationData) {

        List<CapacityEntity> capacityEntities;

        if (!Constants.DEFAULT_QUERY_ORDER.equalsIgnoreCase(paginationData.property())) {

            capacityEntities = advancedQuery(paginationData);
            return capacityEntityMapper.ToModelList(capacityEntities);
        }

        Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
        Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size(), sort);

        capacityEntities = capacityRepository.findAll(pagination).getContent();
        return capacityEntityMapper.ToModelList(capacityEntities);
    }

   /* private Pageable simplePagination(PaginationData paginationData) {
        Sort sort = Sort.by(Sort.Direction.fromString(paginationData.direction()), paginationData.property());
        return PageRequest.of(paginationData.page(), paginationData.size(), sort);
    }

    */

    private List<CapacityEntity> advancedQuery(PaginationData paginationData) {

        Pageable pagination = PageRequest.of(paginationData.page(), paginationData.size());
        Specification<CapacityEntity> specification = queryWithSpecification(paginationData.direction());

        return capacityRepository.findAll(specification,pagination).getContent();
    }

    private Specification<CapacityEntity> queryWithSpecification(String direction){

        return (root, query, criteriaBuilder) -> {

            Join<CapacityEntity, TechnologyEntity> capacityTechnologyMapping = root.join(CapacityEntity.FIELD_CONTAINING_RELATIONSHIP, JoinType.LEFT);
            query.groupBy(root.get("id"));
            Expression<Long> count = criteriaBuilder.count(capacityTechnologyMapping);

            Order order = direction.equals(Sort.Direction.ASC.name()) ?
                    criteriaBuilder.asc(count) : criteriaBuilder.desc(count);
            query.orderBy(order);

            return criteriaBuilder.conjunction();
        };
    }
}
