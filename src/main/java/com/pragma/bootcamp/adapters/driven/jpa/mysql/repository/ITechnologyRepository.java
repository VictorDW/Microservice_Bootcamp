package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITechnologyRepository extends JpaRepository<TechnologyEntity, Long > {
  Optional<TechnologyEntity> findByNameIgnoreCase(String name);
}
