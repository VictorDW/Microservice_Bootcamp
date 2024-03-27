package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICapacityRepository extends JpaRepository<CapacityEntity, Long>, JpaSpecificationExecutor<CapacityEntity> {

    Optional<CapacityEntity> findByNameIgnoreCase(String name);
}
