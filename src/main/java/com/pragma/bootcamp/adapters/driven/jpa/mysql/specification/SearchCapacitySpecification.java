package com.pragma.bootcamp.adapters.driven.jpa.mysql.specification;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class SearchCapacitySpecification implements Specification<CapacityEntity> {

    private final String direction;
    public SearchCapacitySpecification(String direction) {
        this.direction = direction;
    }

    @Override
    public Predicate toPredicate(Root<CapacityEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        final String NAME_FIELD = "technologyEntities";

        Join<CapacityEntity, TechnologyEntity> capacityTechnologyMapping = root.join(NAME_FIELD, JoinType.LEFT);
        query.groupBy(root.get("id"));

        Expression<Long> count = criteriaBuilder.count(capacityTechnologyMapping);

        Order order = direction.equals("ASC") ?
                criteriaBuilder.asc(count) : criteriaBuilder.desc(count);

        query.orderBy(order);


        return criteriaBuilder.conjunction();
    }
}
