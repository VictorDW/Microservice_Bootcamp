package com.pragma.bootcamp.adapters.driven.jpa.mysql.util;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface IQuerySpecificationProvider<T,K> {

  default Specification<T> queryWithSpecification(String direction, String fieldRelationship){

    return (root, query, criteriaBuilder) -> {

      Join<T, K> jointRelationship = root.join(fieldRelationship, JoinType.LEFT);
      query.groupBy(root.get("id"));
      Expression<Long> count = criteriaBuilder.count(jointRelationship);

      Order order = direction.equals(Sort.Direction.ASC.name()) ?
          criteriaBuilder.asc(count) : criteriaBuilder.desc(count);
      query.orderBy(order);

      return criteriaBuilder.conjunction();
    };
  }
}
